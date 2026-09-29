"use client";
import {
  Box,
  Card,
  Grid,
  Stack,
  TextField,
  Typography,
  IconButton,
  InputAdornment,
  CardContent,
  Button,
  InputLabel,
  Alert,
} from "@mui/material";
import {
  Visibility,
  VisibilityOff,
  EmailOutlined,
  LockOutlined,
} from "@mui/icons-material";
import { useEffect, useState } from "react";
import authService from "@/services/authService";
import { useRouter } from "next/navigation";
import LeftSide from "./LeftSideWelcomePage";

type WelcomePageProps = {
  title: string;
  description: string;
  register: boolean;
  login: boolean;
};

export default function WelcomePage({ props }: { props: WelcomePageProps }) {
  const router = useRouter();
  const [showPassword, setShowPassword] = useState(false);
  const [loginVerification, setLoginVerification] = useState("");
  const [createAccountDetails, setCreateAccountDetails] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });
  const [loginAccountDetails, setLoginAccountDetails] = useState({
    email: "",
    password: "",
  });
  const [errors, setErrors] = useState({
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  });

  const handleClickShowPassword = () => {
    setShowPassword(!showPassword);
  };

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) router.replace("/portfolio");
  }, []);

  const handleSubmit = async () => {
    if (props.register) {
      const data = createAccountDetails; //validations left

      await authService
        .create(data)
        .then((response) => localStorage.setItem("token", response))
        .catch((response) => console.log(response));
    } else {
      const data = loginAccountDetails;
      try {
        const response = await authService.login(data);
        localStorage.setItem("token", response.data);
        router.push("/portfolio");
      } catch (error: any) {
        if (error.response?.status === 401) {
          setLoginVerification("Invalid password or email address");
        } else {
          console.error("Unable to login user", error);
        }
      }
    }
  };
  return (
    <Grid
      container
      // 1. Lowered breakpoint from 'md' to 'sm' (600px) so it splits on almost all laptops/monitors
      direction={{ xs: "row-reverse", sm: "row" }}
      sx={{
        width: "100vw",
        minHeight: "100vh",
        m: 0,
        p: 0,
        overflowX: "hidden",
      }}
    >
      <LeftSide />
      <Grid
        item
        xs={12}
        sm={6}
        sx={{
          flexBasis: { xs: "100%", sm: "50%" },
          maxWidth: { xs: "100%", sm: "50%" },
          height: { xs: "auto", sm: "100vh" },
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          p: 4,
          boxSizing: "border-box",
        }}
      >
        <Card
          elevation={8}
          sx={{
            maxWidth: 440,
            width: "100%",
            mx: "auto",
            bgcolor: "background.paper",
            color: "text.primary",
            border: "none",
          }}
        >
          <CardContent sx={{ p: { xs: 2, sm: 4 } }}>
            <Box
              component="form"
              noValidate
              autoComplete="off"
              onSubmit={async (e) => {
                e.preventDefault();
                await handleSubmit();
              }}
            >
              <Stack spacing={3}>
                <Box>
                  <Box>
                    <Typography
                      variant="h5"
                      align="center"
                      sx={{ fontWeight: "bold", mb: 0, color: "text.primary" }}
                    >
                      {props.title}
                    </Typography>
                    <Typography
                      variant="subtitle1"
                      align="center"
                      sx={{ color: "text.secondary", mb: 3 }}
                    >
                      {props.description}
                    </Typography>
                  </Box>
                  <Box>
                    {loginVerification.length !== 0 && (
                      <Alert variant="outlined" severity="error">
                        {loginVerification}
                      </Alert>
                    )}
                    {props.register && (
                      <>
                        <Box>
                          <InputLabel
                            required
                            size="small"
                            variant="standard"
                            // error={errors}
                            sx={{ color: "text.primary", mb: 3 }}
                          >
                            First Name
                          </InputLabel>
                          <TextField
                            required
                            id="firstName"
                            variant="outlined"
                            name="firstName"
                            value={createAccountDetails.firstName}
                            autoComplete="name"
                            autoFocus
                            fullWidth
                            slotProps={{
                              htmlInput: { maxLength: 24 },
                            }}
                            // error={errors}
                            placeholder="Enter Your First Name"
                            onChange={(e) => {
                              setCreateAccountDetails({
                                ...createAccountDetails,
                                firstName: e.target.value,
                              });
                            }}
                          />
                        </Box>
                        <Box>
                          <InputLabel
                            required
                            size="small"
                            variant="standard"
                            // error={errors}
                            sx={{ color: "text.primary", mb: 3 }}
                          >
                            Last Name
                          </InputLabel>
                          <TextField
                            required
                            fullWidth
                            value={createAccountDetails.lastName}
                            id="lastName"
                            variant="outlined"
                            name="lastName"
                            autoComplete="family-name"
                            placeholder="Enter Your Last Name"
                            slotProps={{
                              htmlInput: {
                                maxLength: 24,
                              },
                            }}
                            onChange={(e) => {
                              setCreateAccountDetails({
                                ...createAccountDetails,
                                lastName: e.target.value,
                              });
                            }}
                          />
                        </Box>
                      </>
                    )}
                    <Box sx={{ mb: 0 }}>
                      <InputLabel
                        required
                        size="small"
                        variant="standard"
                        sx={{ color: "text.primary", mb: 3 }}
                      >
                        Email Address
                      </InputLabel>
                      <TextField
                        required
                        fullWidth
                        id="email"
                        variant="outlined"
                        name="email"
                        autoComplete="email"
                        value={
                          props.register
                            ? createAccountDetails.email
                            : loginAccountDetails.email
                        }
                        placeholder="Enter Your Email Address"
                        slotProps={{
                          input: {
                            startAdornment: (
                              <InputAdornment position="start">
                                <EmailOutlined />
                              </InputAdornment>
                            ),
                          },
                          htmlInput: {
                            maxLength: 30,
                          },
                        }}
                        onChange={(e) =>
                          props.register
                            ? setCreateAccountDetails({
                                ...createAccountDetails,
                                email: e.target.value,
                              })
                            : setLoginAccountDetails({
                                ...loginAccountDetails,
                                email: e.target.value,
                              })
                        }
                      />
                    </Box>
                    <Box>
                      <InputLabel
                        required
                        size="small"
                        variant="standard"
                        // error={errors}
                        sx={{ color: "text.primary", mb: 3 }}
                      >
                        Password
                      </InputLabel>
                      <TextField
                        required
                        fullWidth
                        id="password"
                        placeholder="Enter Your Password"
                        variant="outlined"
                        value={
                          props.register
                            ? createAccountDetails.password
                            : loginAccountDetails.password
                        }
                        name="password"
                        type={showPassword ? "text" : "password"}
                        autoComplete="current-password"
                        onChange={(e) => {
                          props.register
                            ? setCreateAccountDetails({
                                ...createAccountDetails,
                                password: e.target.value,
                              })
                            : setLoginAccountDetails({
                                ...loginAccountDetails,
                                password: e.target.value,
                              });
                        }}
                        slotProps={{
                          input: {
                            startAdornment: (
                              <InputAdornment position="start">
                                <LockOutlined />
                              </InputAdornment>
                            ),
                            endAdornment: (
                              <InputAdornment position="end">
                                <IconButton
                                  aria-label="toggle password visibility"
                                  onClick={handleClickShowPassword}
                                  edge="end"
                                >
                                  {showPassword ? (
                                    <Visibility />
                                  ) : (
                                    <VisibilityOff />
                                  )}
                                </IconButton>
                              </InputAdornment>
                            ),
                          },
                        }}
                      />
                    </Box>
                  </Box>
                </Box>
              </Stack>
              <Stack
                direction="row"
                spacing={1}
                sx={{ p: 2, justifyContent: "center", mt: 2 }}
              >
                <Button
                  type="submit"
                  variant="contained"
                  sx={{
                    backgroundColor: "#1b5cdf",
                    "&:hover": { bgcolor: "primary.dark" },
                  }}
                >
                  {props.register ? "Sign Up" : "Sign In"}
                </Button>
              </Stack>
            </Box>
          </CardContent>
        </Card>
      </Grid>
    </Grid>
  );
}
