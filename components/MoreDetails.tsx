import { Holdings } from "@/types/portfolioHoldingsTypes";
import { Box, Typography } from "@mui/material";

export default function MoreDetails({ props }: { props: Holdings }) {
  return (
    <Box
      sx={{
        minHeight: 150,
        py: 2,
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        width: "100%",
      }}
    >
      <Box sx={{ textAlign: "center" }}>
        <Typography variant="body2">Company:</Typography>
        <Typography variant="caption">{props.companyName}</Typography>
      </Box>
      <Box sx={{ textAlign: "center" }}>
        <Typography variant="body2">Exchange:</Typography>
        <Typography variant="caption">{props.exchange}</Typography>
      </Box>
      <Box sx={{ textAlign: "center" }}>
        <Typography variant="body2">Sector:</Typography>
        <Typography variant="caption">{props.sector}</Typography>
      </Box>
      <Box sx={{ textAlign: "center" }}>
        <Typography variant="body2">Industry:</Typography>
        <Typography variant="caption">{props.industry}</Typography>
      </Box>
      <Box sx={{ textAlign: "center" }}>
        <Typography variant="body2">Invested Amount:</Typography>
        <Typography variant="body2">${props.investedAmount.toFixed(2)}</Typography>
      </Box>
      <Box sx={{ textAlign: "center" }}>
        <Typography variant="body2">Change:</Typography>
        <Typography
          variant="body2"
          color={props.dailyChange > 0 ? "success" : "error"}
        >
          {`${props.dailyChange > 0 ? `+${props.dailyChange.toFixed(2)} (+${props.dailyChangePercent.toFixed(2)}%)` : `${props.dailyChange.toFixed(2)} (${props.dailyChangePercent.toFixed(2)}%)`}`}
        </Typography>
      </Box>
      <Box sx={{ textAlign: "center" }}>
        <Typography variant="body2">Previous Close:</Typography>
        <Typography variant="body2">
          {`$${props.previousClose.toFixed(2)}`}
        </Typography>
      </Box>
    </Box>
  );
}
