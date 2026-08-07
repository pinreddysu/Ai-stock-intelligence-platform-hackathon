"use client";
import {
  Alert,
  AppBar,
  Box,
  Button,
  Card,
  CardActionArea,
  CardActions,
  CardContent,
  CircularProgress,
  Container,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Grid,
  TextField,
  Toolbar,
  Typography,
} from "@mui/material";
import PersonIcon from "@mui/icons-material/Person";
import AddIcon from "@mui/icons-material/Add";
import { useEffect, useState } from "react";
import Logo from "@/components/Logo";
import PortfolioPageHeader from "@/components/PortfolioPageHeader";
import { useRouter } from "next/navigation";
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";
import ViewListIcon from "@mui/icons-material/ViewList";
import { Search } from "@mui/icons-material";
import portfolioService from "@/services/portfolioService";
import { init } from "next/dist/compiled/webpack/webpack";

interface Portfolio {
  id: number;
  portfolioName: string;
  description: string;
  holdings: number;
}

export default function Portfolio() {
  const router = useRouter();
  const [open, setOpen] = useState(false);
  const [noResultsFound, setNoResultsFound] = useState("");
  const [editPortfolio, setEditPortfolio] = useState(false);
  const [searchValue, setSearchValue] = useState("");
  const [portfolio, setPortfolio] = useState({
    portfolioName: "",
    description: "",
  });
  const [orignalPortfolio, setOriginalPortfolio] = useState({
    portfolioName: "",
    description: "",
  });
  const [loading, setLoading] = useState(false);
  const [initialLoading, setIntialLoading] = useState(true);
  const [selectedPortfolioId, setSelectedPortfolioId] = useState<number | null>(
    null,
  );
  const [userPortfolio, setUserPortfolio] = useState<Portfolio[]>([]);
  const handleOpen = () => {
    setOpen(true);
  };
  const handleClose = () => {
    setOpen(false);
    if (editPortfolio) {
      setEditPortfolio(false);
      setSelectedPortfolioId(null);
      setOriginalPortfolio({
        portfolioName: "",
        description: "",
      });
    }
    setPortfolio({
      portfolioName: "",
      description: "",
    });
  };

  const handleDelete = async (portfolio: Portfolio) => {
    const portfolioId = portfolio.id;
    console.log(portfolioId);
    try {
      const response = await portfolioService.deleteUserPortfolioById({
        id: portfolioId,
      });
      console.log(response);
      getPortfolios();
    } catch (error) {
      console.log(error);
    } finally {
      setLoading(false);
    }
  };

  const handleEdit = (portfolio: Portfolio) => {
    setEditPortfolio(true);
    setSelectedPortfolioId(portfolio.id);
    const data = {
      portfolioName: portfolio.portfolioName,
      description: portfolio.description,
    };
    setOriginalPortfolio(data);
    setPortfolio(data);
    handleOpen();
  };

  const hasChange =
    portfolio.portfolioName !== orignalPortfolio.portfolioName ||
    portfolio.description !== orignalPortfolio.description;

  const getPortfolios = async () => {
    try {
      const response = await portfolioService.getAllUserPortfolio("");
      setUserPortfolio(response.data);
      setIntialLoading(false);
    } catch (error: any) {
      if (error.response?.status === 401 || error.code === "ERR_NETWORK") {
        localStorage.removeItem("token");
        router.replace("/login");
      } else {
        console.error("Failed to fetch portfolios", error);
      }
    }
  };

  useEffect(() => {
    const timer = setTimeout(() => {
      handleSearch(searchValue);
    }, 500);

    return () => clearTimeout(timer);
  }, [searchValue]);

  useEffect(() => {
    getPortfolios();
  }, []);

  const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    setLoading(true);
    if (selectedPortfolioId) {
      try {
        console.log(portfolio);
        await portfolioService
          .editUserPortfolio({ ...portfolio, id: selectedPortfolioId })
          .then((response) => console.log(response));
        getPortfolios();
        console.log(portfolio);
      } catch (error) {
        console.log(error);
      } finally {
        setLoading(false);
        handleClose();
      }
    } else {
      try {
        await portfolioService
          .createPortfolio(portfolio)
          .then((response) => console.log(response));
        getPortfolios();
      } catch (error) {
        console.log(error);
      } finally {
        setLoading(false);

        handleClose();
      }
    }
    setLoading(false);
    handleClose();
  };
  const handleSearch = async (value: string) => {
    try {
      const response = await portfolioService.getAllUserPortfolio(value.trim());
      if (response.data.length === 0) {
        setNoResultsFound("No results found");
      } else {
        setNoResultsFound("");
      }
      setUserPortfolio(response.data);
    } catch (error: any) {
      console.error(error);
    }
  };

  return (
    <Box>
      {initialLoading && (
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            width: "100vw",
            height: "100vh",
          }}
        >
          <CircularProgress />
        </Box>
      )}
      {initialLoading === false && (
        <Box>
          <AppBar position="static">
            <Toolbar>
              <Logo />
              <Button
                variant="contained"
                sx={{
                  ml: "auto",
                }}
                startIcon={<PersonIcon />}
              >
                Logout
              </Button>
            </Toolbar>
          </AppBar>
          <Container maxWidth="xl">
            <Box
              sx={{
                py: 4,
                display: "flex",
                alignItems: "center",
              }}
            >
              <PortfolioPageHeader />
              <Button
                variant="contained"
                sx={{
                  ml: "auto",
                }}
                onClick={handleOpen}
                startIcon={<AddIcon />}
              >
                Create Portfolio
              </Button>
              <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
                <Box component="form" onSubmit={handleSubmit}>
                  <DialogTitle>
                    {editPortfolio ? (
                      <Typography
                        component="span"
                        variant="h5"
                        sx={{ fontWeight: "bold" }}
                      >
                        Edit Portfolio
                      </Typography>
                    ) : (
                      <Typography
                        component="span"
                        variant="h5"
                        sx={{ fontWeight: "bold" }}
                      >
                        Create Portfolio
                      </Typography>
                    )}
                  </DialogTitle>
                  <DialogContent>
                    <DialogContentText>
                      <Typography component="span" variant="body1">
                        Please fill out required fields
                      </Typography>
                    </DialogContentText>
                    <TextField
                      autoFocus
                      required
                      margin="dense"
                      onChange={(e) => {
                        setPortfolio({
                          ...portfolio,
                          portfolioName: e.target.value,
                        });
                      }}
                      id="portfolioName"
                      name="portfolioName"
                      label="Portfolio Name"
                      value={portfolio.portfolioName}
                      fullWidth
                      variant="outlined"
                      slotProps={{
                        htmlInput: { maxLength: 50 },
                      }}
                    />
                    <TextField
                      margin="dense"
                      multiline
                      onChange={(e) => {
                        setPortfolio({
                          ...portfolio,
                          description: e.target.value,
                        });
                      }}
                      id="portfolioDescription"
                      name="portfolioDescription"
                      value={portfolio.description}
                      label="Portfolio Description"
                      fullWidth
                      variant="outlined"
                      rows={3}
                      slotProps={{
                        htmlInput: { maxLength: 250 },
                      }}
                    />
                  </DialogContent>
                  <DialogActions>
                    <Button onClick={handleClose} disabled={loading}>
                      Cancel
                    </Button>
                    {editPortfolio ? (
                      <Button
                        type="submit"
                        disabled={
                          loading ||
                          !hasChange ||
                          portfolio.portfolioName.trim() === "" ||
                          portfolio.description.trim() === ""
                        }
                      >
                        {loading ? "Updating" : "update"}
                      </Button>
                    ) : (
                      <Button
                        type="submit"
                        disabled={
                          loading || portfolio.portfolioName.trim().length === 0
                        }
                      >
                        {loading ? "Creating" : "Create"}
                      </Button>
                    )}
                  </DialogActions>
                </Box>
              </Dialog>
            </Box>
          </Container>
          <Box
            sx={{ mb: 3, px: 3, justifyContent: "flex-start", display: "flex" }}
          >
            <TextField
              variant="outlined"
              id="searchPortfolio"
              name="searchPortfolio"
              value={searchValue}
              sx={{ width: "50%" }}
              placeholder=" Search portfolio"
              onChange={(e) => {
                setSearchValue(e.target.value);
              }}
              slotProps={{
                input: {
                  startAdornment: <Search />,
                },
                htmlInput: {
                  maxLength: 50,
                },
              }}
            ></TextField>
          </Box>

          {noResultsFound.length > 0 && (
            <Box
              sx={{
                mb: 3,
                px: 3,
                justifyContent: "flex-start",
                display: "flex",
              }}
            >
              <Alert variant="outlined" severity="warning">
                {noResultsFound}
              </Alert>
            </Box>
          )}
          <Grid container sx={{ py: 3, px: 3 }} spacing={5}>
            {userPortfolio.map((item) => (
              <Grid size={{ xs: 12, sm: 6, md: 4 }} key={item.id}>
                <Card
                  key={item.id}
                  elevation={4}
                  sx={{
                    height: "100%",
                    borderRadius: 3,
                    cursor: "pointer",
                    transition: "0.2s",
                    "&:hover": {
                      transform: "translateY(-4px)",
                      boxShadow: 8,
                    },
                  }}
                >
                  <CardActionArea
                    onClick={() => router.push("/portfolioDetails")}
                  >
                    <CardContent>
                      <Typography variant="h6" sx={{ fontWeight: "bold" }}>
                        {item.portfolioName}
                      </Typography>
                      <Typography
                        variant="body1"
                        color="text.secondary"
                        sx={{ mt: 1, minHeight: 40 }}
                      >
                        {item.description}
                      </Typography>
                      {item.holdings < 1 ? (
                        <Typography sx={{ mt: 3 }}>
                          {item.holdings} holding
                        </Typography>
                      ) : (
                        <Typography sx={{ mt: 3 }}>
                          {" "}
                          {item.holdings} holdings
                        </Typography>
                      )}
                    </CardContent>
                  </CardActionArea>
                  <CardActions
                    sx={{
                      justifyContent: "space-between",
                      px: 2,
                      boxShadow: 12,
                    }}
                  >
                    <Button
                      size="small"
                      onClick={() => handleEdit(item)}
                      startIcon={<EditIcon />}
                    >
                      Edit
                    </Button>
                    <Button
                      size="small"
                      startIcon={<ViewListIcon />}
                      onClick={() =>
                        router.push(`/portfolioDetails/${item.id}`)
                      }
                    >
                      View Portfolio
                    </Button>
                    <Button
                      size="small"
                      color="error"
                      onClick={() => handleDelete(item)}
                      startIcon={<DeleteIcon />}
                    >
                      Delete
                    </Button>
                  </CardActions>
                </Card>
              </Grid>
            ))}
          </Grid>
        </Box>
      )}
    </Box>
  );
}
