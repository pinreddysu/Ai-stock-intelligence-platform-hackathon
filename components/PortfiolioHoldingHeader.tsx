"use client";
import { PortfolioInfo } from "@/types/portfolioInfoTypes";
import { Box, Button, Typography } from "@mui/material";

export default function PortfolioHoldingHeader({
  props,
}: {
  props: PortfolioInfo;
}) {
  return (
    <Box
      sx={{
        display: "flex",
        width: "100%",
        justifyContent: "space-between",
        gap: 2,

        flexDirection: {
          xs: "column",
          sm: "row",
        },
      }}
    >
      <Box
        sx={{
          minWidth: 0,
          flex: 1,
        }}
      >
        <Typography
          variant="h3"
          sx={{
            overflowWrap: "break-word",
          }}
        >
          {props.portfolioName}
        </Typography>

        <Typography
          variant="h6"
          sx={{
            mt: 1,
            overflowWrap: "break-word",
          }}
        >
          {props.description}
        </Typography>

        <Typography
          variant="body1"
          sx={{
            mt: 0.5,
            fontStyle: "italic",
          }}
        >
          {props.portfolioHoldings === 1
            ? `Total Holdings: ${props.portfolioHoldings}`
            : `Total Holding: ${props.portfolioHoldings}`}
        </Typography>
      </Box>
      <Box
        sx={{
          display: "flex",
          gap: 1,
          alignSelf: {
            xs: "flex-start",
            sm: "center",
          },

          flexShrink: 0,
        }}
      >
        <Button variant="outlined">Edit</Button>

        <Button variant="outlined" color="error">
          Delete
        </Button>
      </Box>
    </Box>
  );
}
