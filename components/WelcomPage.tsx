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
} from "@mui/material";
import { Visibility, VisibilityOff } from "@mui/icons-material";
import { useState } from "react";

type WelcomePageProps = {
  title: string;
  description: string;
  firstName: boolean;
  lastName: boolean;
};

export default function WelcomePage({ props }: { props: WelcomePageProps }) {
  const [showPassword, setShowPassword] = useState(false);
  const [password, setPassword] = useState("");
  const [email, setEmail] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [errors, setErrors] = useState(false);
  const handleClickShowPassword = () => {
    setShowPassword(!showPassword);
  };
  const text = [
    {
      title: "Portfolio Tracking",
      description: (
        <div>
          Track and manage all your portfolios <br /> in one place
        </div>
      ),
    },
    {
      title: " AI Insights",
      description: (
        <div>
          Get AI-driven insights and <br /> recommendations
        </div>
      ),
    },
    {
      title: "Performance Analytics",
      description: (
        <div>
          Analyze performance with advanced <br /> charts and metrics
        </div>
      ),
    },
    {
      title: "Market Intelligence",
      description: (
        <div>
          Stay updated with market trends
          <br /> and relevant news
        </div>
      ),
    },
  ];

  const handleSubmit = (event: React.SubmitEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (props.firstName) {
      const data = {
        firstName: firstName,
        lastName: lastName,
        email: email,
        passwordHash: password,
      };
      return data;
    } else {
      const data = {
        email: email,
        passwordHash: password,
      };
      return data;
    }
  };
  return (
    <Grid
      container
      // 1. Lowered breakpoint from 'md' to 'sm' (600px) so it splits on almost all laptops/monitors
      direction={{ xs: "column", sm: "row" }}
      sx={{
        width: "100vw",
        minHeight: "100vh",
        m: 0,
        p: 0,
        overflowX: "hidden",
      }}
    >
      <Grid
        item
        xs={12}
        sm={6}
        sx={{
          // 2. Hardcode flex properties to force the 50% split engine to cooperate
          flexBasis: { xs: "100%", sm: "50%" },
          maxWidth: { xs: "100%", sm: "50%" },
          height: { xs: "auto", sm: "100vh" },
          mb: { xs: 6, sm: 0 },
          pb: { xs: 4, sm: 0 },
          backgroundImage: `url(${"/leftSide.png"})`,
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundRepeat: "no-repeat",
          display: "flex",
          flexDirection: "column",
          justifyContent: "flex-start",
          alignItems: "flex-start",
          pt: 6,
          pl: 4,
          boxSizing: "border-box",
        }}
      >
        <Box sx={{ display: "flex", gap: 2, alignItems: "center" }}>
          <Typography
            variant="h3"
            sx={{ fontWeight: "bold", color: "#ffffff" }}
          >
            PIE
          </Typography>
          <Typography variant="subtitle2" sx={{ color: "#ffffff" }}>
            AI Portfolio
            <br />
            Intelligence Engine
          </Typography>
        </Box>
        <Typography
          variant="h4"
          sx={{ fontWeight: "bold", color: "#ffffff", mt: 8 }}
        >
          AI-Powered Insights.
        </Typography>
        <Box sx={{ display: "flex", gap: 1, alignItems: "center" }}>
          <Typography
            variant="h4"
            sx={{ fontWeight: "bold", color: "#ffffff", mt: 0 }}
          >
            Smarter
          </Typography>
          <Typography
            variant="h4"
            sx={{ fontWeight: "bold", color: "#16a0b2", mt: 0 }}
          >
            Investments.
          </Typography>
        </Box>
        <Typography variant="subtitle1" sx={{ color: "#ffffff", mt: 3 }}>
          Track your portfolios, analyze performance,
          <br />
          and get AI-driven insights to make
          <br />
          smarter investment decisions.
        </Typography>

        <Box sx={{ mt: 4 }}>
          {text.map((item) => (
            <Typography
              key={item.title}
              variant="subtitle2"
              sx={{ fontWeight: "bold", color: "#ffffff", mt: 2 }}
            >
              {item.title}
              <br />
              <Typography
                variant="caption"
                sx={{ color: "#ffffff", mt: 0, fontSize: "0.75rem" }}
              >
                {item.description}
              </Typography>
            </Typography>
          ))}
        </Box>
      </Grid>

      {/* Right side Grid Item (Bottom on Mobile / Right on Desktop) */}
      <Grid
        item="true"
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
          elevation={3}
          sx={{
            maxWidth: 440,
            width: "100%",
            mx: "auto",
            bgcolor: "#ffffff",
            border: "none",
          }}
        >
          <CardContent sx={{ p: { xs: 2, sm: 4 } }}>
            <Box component="form" noValidate autoComplete="off">
              <Stack spacing={3}>
                <Box>
                  <Typography
                    variant="h5"
                    align="center"
                    sx={{ fontWeight: "bold", mb: 1 }}
                  >
                    {props.title}
                  </Typography>
                  <Typography
                    variant="subtitle1"
                    align="center"
                    sx={{ color: "#666666" }}
                  >
                    {props.description}
                  </Typography>
                </Box>
                {props.firstName && (
                  <TextField
                    required
                    fullWidth
                    id="firstName"
                    label="First Name"
                    variant="outlined"
                    name="firstName"
                    autoComplete="name"
                    autoFocus
                    error={errors}
                    onChange={(e) => {
                        if(e.target.value.length === 0){
                            setErrors(true)
                        }
                        else{
                            setFirstName(e.target.value);
                        }
                      
                    }}
                  />
                )}
                {props.lastName && (
                  <TextField
                    required
                    fullWidth
                    id="lastName"
                    label="Last Name"
                    variant="outlined"
                    name="lastName"
                    autoComplete="family-name"
                    onChange={(e) => {
                      setLastName(e.target.value);
                    }}
                  />
                )}
                <TextField
                  required
                  fullWidth
                  id="email"
                  label="Email Address"
                  variant="outlined"
                  name="email"
                  autoComplete="email"
                  onChange={(e) => setEmail(e.target.value)}
                />
                <TextField
                  required
                  fullWidth
                  id="password"
                  label="Password"
                  variant="outlined"
                  name="password"
                  type={showPassword ? "text" : "password"}
                  autoComplete="current-password"
                  onChange={(e) => {
                    setPassword(e.target.value);
                  }}
                  slotProps={{
                    input: {
                      endAdornment: (
                        <InputAdornment position="end">
                          <IconButton
                            aria-label="toggle password visibility"
                            onClick={handleClickShowPassword}
                            edge="end"
                          >
                            {showPassword ? <Visibility /> : <VisibilityOff />}
                          </IconButton>
                        </InputAdornment>
                      ),
                    },
                  }}
                />
              </Stack>
            </Box>
          </CardContent>
          <Stack
            direction="row"
            spacing={1}
            sx={{ p: 2, justifyContent: "center" }}
          >
            <Button
              variant="contained"
              onClick={handleSubmit}
              sx={{ backgroundColor: "#1b5cdf" }}
            >
              {props.title === "Welcome Back" ? "Sign In" : "Sign Up"}
            </Button>
          </Stack>
        </Card>
      </Grid>
    </Grid>
  );
}
