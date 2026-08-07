"use client"
import { Box, Grid, Typography } from "@mui/material";
import Logo from "./Logo";

export default function LeftSide() {
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
      title: "AI Insights",
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
  return (
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
      <Logo />
      <Typography
        variant="h4"
        sx={{ fontWeight: "bold", color: "common.white", mt: 8 }}
      >
        AI-Powered Insights.
      </Typography>
      <Box sx={{ display: "flex", gap: 1, alignItems: "center" }}>
        <Typography
          variant="h4"
          sx={{ fontWeight: "bold", color: "common.white", mt: 0 }}
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
      <Typography variant="subtitle1" sx={{ color: "common.white", mt: 3 }}>
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
            sx={{ fontWeight: "bold", color: "common.white", mt: 2 }}
          >
            {item.title}
            <br />
            <Typography
              variant="caption"
              sx={{ color: "common.white", mt: 0, fontSize: "0.75rem" }}
            >
              {item.description}
            </Typography>
          </Typography>
        ))}
      </Box>
    </Grid>
  );
}
