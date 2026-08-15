"use client";

import {
  Alert,
  AppBar,
  Box,
  Button,
  Container,
  Snackbar,
  SnackbarCloseReason,
  Toolbar,
  Typography,
} from "@mui/material";
import { useEffect, useRef, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import portfolioHoldingService from "@/services/portfolioHoldingService";
import Logo from "@/components/Logo";
import PortfolioDetailsPageHeader from "@/components/PortfolioDetailsPageHeader";
import {
  EditHoldingRequest,
  Holdings,
  PortfolioDetailsAndHoldings,
} from "@/types/portfolioHoldingsTypes";
import PortfolioHoldingHeader from "@/components/PortfiolioHoldingHeader";
import PortfolioStats from "@/components/PortfolioStats";
import SearchBar from "@/components/SearchBar";
import AddOutlinedIcon from "@mui/icons-material/AddOutlined";
import PortfolioHoldingsStandardTable from "@/components/PortfolioHoldingsStandardTable";
import portfolioService from "@/services/portfolioService";

interface Portfolio {
  id: number;
  portfolioName: string;
}

export default function PortfolioDetails() {
  const [open, setOpen] = useState(false);
  const [portfolioHoldings, setPortfolioHoldings] =
    useState<PortfolioDetailsAndHoldings>();
  const [portfolioHoldingsTableData, setPortfolioHoldingsTableData] =
    useState<Holdings[]>();
  const [filteredPortfolioHoldings, setFilteredPortfolioHoldings] = useState(0);
  const [updateHoldingValues, setUpdateHoldingValues] =
    useState<EditHoldingRequest>();
  const params = useParams();
  const portfolioId = Number(params.id);
  const [searchValue, setSearchValue] = useState("");
  const [pageData, setPageData] = useState(0);
  const [sizeData, setSizeData] = useState(1);
  const [deleteHolding, setDeleteHolding] = useState<number>();

  const router = useRouter();

  const [openSnackBar, setOpenSnackBar] = useState("");
  const handleCloseSnackBar = (
    event: React.SyntheticEvent | Event,
    reason?: SnackbarCloseReason,
  ) => {
    if (reason === "clickaway") {
      return;
    }

    setOpenSnackBar("");
  };

  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const requestId = useRef(0);

  const getAllHoldingsById = async (
    search: string,
    rowSize: number,
    page: number,
  ) => {
    const currentRequestId = ++requestId.current;
    try {
      const response = await portfolioHoldingService.getHoldingsByPortfolioId(
        {
          portfolioId: portfolioId,
        },
        search,
        rowSize,
        page,
      );

      if (currentRequestId !== requestId.current) {
        return;
      }
      setPortfolioHoldings(response.data);
      setPortfolioHoldingsTableData(response.data?.holdings ?? []);
      setFilteredPortfolioHoldings(
        response.data.filteredPortfolioHoldings ?? 0,
      );
    } catch (error: any) {
      if (currentRequestId !== requestId.current) {
        return;
      }
      console.error(error);
    }
  };

  useEffect(() => {
    const timer = setTimeout(() => {
      getAllHoldingsById(searchValue, sizeData, pageData);
    }, 500);
    return () => clearTimeout(timer);
  }, [portfolioId, searchValue, sizeData, pageData]);

  const updateHoldingData = async () => {
    try {
      if (updateHoldingValues) {
        // console.log(updateHoldingValues);
        const response =
          await portfolioHoldingService.updatePortfolioHoldings(
            updateHoldingValues,
          );

        getAllHoldingsById(searchValue, sizeData, pageData);
        setOpenSnackBar(response.data);
      }
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    updateHoldingData();
  }, [updateHoldingValues]);

  const handleSearch = (value: string) => {
    setSearchValue(value);
    setPageData(0);
  };

  const handleRefreshData = (data: PortfolioDetailsAndHoldings) => {
    setPortfolioHoldings(data);
    setPortfolioHoldingsTableData(data.holdings ?? []);
    handleClick();
  };

  const deleteHoldingData = async () => {
    try {
      if (deleteHolding) {
        const response = await portfolioHoldingService.deletePortfolioHOldings({
          id: deleteHolding,
        });
        getAllHoldingsById(searchValue, sizeData, pageData);
        setOpenSnackBar(response.data);
      }
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    deleteHoldingData();
  }, [deleteHolding]);

  return (
    <Box>
      <AppBar position="static">
        <Toolbar>
          <Logo />
        </Toolbar>
      </AppBar>
      <Container maxWidth="xl">
        <Box
          sx={{
            py: 4,
            pb: 1,
            display: "flex",
            alignItems: "center",
          }}
        >
          <PortfolioDetailsPageHeader
            portfolioId={portfolioId}
            refreshData={handleRefreshData}
            searchValue={searchValue}
            page={pageData}
            size={sizeData}
          />
        </Box>
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "flex-end",
            gap: 0.25,
          }}
        >
          <Typography
            variant="caption"
            color="inherit"
            sx={{
              fontStyle: "italic",
            }}
          >{`Market last updated: ${portfolioHoldings?.marketTimeStamp}`}</Typography>
          <Typography
            variant="caption"
            color="inherit"
            sx={{
              fontStyle: "italic",
            }}
          >{`Portfolio last updated: ${portfolioHoldings?.lastUpdatedAt}`}</Typography>
        </Box>
        {portfolioHoldings && (
          <PortfolioHoldingHeader
            props={{
              id: portfolioHoldings.id,
              portfolioName: portfolioHoldings.portfolioName,
              description: portfolioHoldings.description,
              portfolioHoldings: portfolioHoldings.portfolioHoldings,
            }}
          />
        )}
        <Snackbar open={open} autoHideDuration={6000} onClose={handleClose}>
          <Alert
            onClose={handleClose}
            severity="success"
            variant="filled"
            sx={{ width: "100%" }}
          >
            Portfolio details have been updated!
          </Alert>
        </Snackbar>
        <Box sx={{ py: 4 }}>
          {portfolioHoldings && (
            <PortfolioStats
              props={{
                id: portfolioHoldings?.id,
                portfolioValue: portfolioHoldings.portfolioValue,
                portfolioInvested: portfolioHoldings.portfolioInvested,
                gainOrLoss: portfolioHoldings.gainOrLoss,
                gainOrLossPercent: portfolioHoldings.gainOrLossPercent,
                portfolioHoldings: portfolioHoldings.portfolioHoldings,
                lastUpdatedAt: portfolioHoldings.lastUpdatedAt,
                marketTimeStamp: portfolioHoldings.marketTimeStamp,
              }}
            />
          )}
        </Box>
        <Box
          sx={{
            py: 0,
          }}
        >
          <Box
            sx={{
              display: "flex",
              gap: 2,
              justifyContent: { sm: "space-between" },
              flexDirection: { xs: "column", sm: "row" },
            }}
          >
            <SearchBar searchData={handleSearch} page="portfolioDetails" />
            <Button
              startIcon={<AddOutlinedIcon />}
              size="small"
              variant="contained"
              onClick={() =>
                router.push(`/portfolioDetails/${portfolioId}/addHoldings`)
              }
            >
              Add Holdings
            </Button>
          </Box>
          {portfolioHoldings && portfolioHoldingsTableData != null && (
            <Box sx={{ py: 2 }}>
              <PortfolioHoldingsStandardTable
                sizeData={setSizeData}
                pageData={setPageData}
                rows={portfolioHoldings.holdings ?? []}
                totalData={filteredPortfolioHoldings}
                pageNumber={pageData}
                updatedHoldingData={setUpdateHoldingValues}
                deleteHolding={setDeleteHolding}
              />
            </Box>
          )}
        </Box>
      </Container>
      <Snackbar
        anchorOrigin={{ horizontal: "center", vertical: "bottom" }}
        open={openSnackBar !== ""}
        autoHideDuration={1000}
        onClose={handleCloseSnackBar}
      >
        <Alert
          onClose={handleCloseSnackBar}
          severity="success"
          variant="filled"
          sx={{ width: "100%" }}
        >
          {openSnackBar}
        </Alert>
      </Snackbar>
    </Box>
  );
}
