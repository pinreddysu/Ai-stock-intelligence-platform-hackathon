import { AddHoldingsData, StockLists } from "@/types/stocksTypes";
import { Box, Button, InputLabel, TextField, Typography } from "@mui/material";
import { useParams } from "next/navigation";
import { useState } from "react";

type AddHoldingProps = {
  stockData: StockLists;
  cancelHolding: (data: boolean) => void;
  addHoldingData: (data: AddHoldingsData) => void;
};

export default function AddHoldingForm({
  stockData,
  cancelHolding,
  addHoldingData,
}: AddHoldingProps) {
  const params = useParams();
  const portfolioId = Number(params.id);
  const [shares, setShares] = useState<number>(0);
  const [avgPrice, setAvgPrice] = useState<number>(0);

  const handleSubmit = (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    addHoldingData({
      portfolioId: portfolioId,
      ticker: stockData.ticker,
      companyName: stockData.companyName,
      stockId: stockData.id,
      shares: shares,
      avgPrice: avgPrice,
    });
    cancelHolding(true);
  };
  return (
    <Box sx={{ pt: 4 }} component="form" onSubmit={handleSubmit}>
      <Typography variant="h6">Selected Stock:</Typography>
      <Typography sx={{ pt: 1 }}>
        Stock: {stockData.companyName} ({stockData.ticker})
      </Typography>
      <InputLabel required autoFocus sx={{ pt: 1 }}>
        Shares
      </InputLabel>
      <TextField
        required
        autoFocus
        type="number"
        margin="dense"
        fullWidth
        variant="outlined"
        slotProps={{
          htmlInput: { step: ".01" },
        }}
        onChange={(e) => {
          e.preventDefault();
          setShares(Number(e.target.value));
        }}
      />
      <InputLabel required sx={{ pt: 1 }}>
        Price
      </InputLabel>
      <TextField
        type="number"
        margin="dense"
        required
        variant="outlined"
        fullWidth
        error={false}
        onChange={(e) => {
          e.preventDefault();
          setAvgPrice(Number(e.target.value));
        }}
        slotProps={{
          input: {
            startAdornment: "$",
          },
          htmlInput: {
            step: ".01",
          },
        }}
      />
      <Box sx={{ pt: 2, display: "flex", justifyContent: "flex-end", gap: 2 }}>
        <Button
          variant="outlined"
          type="submit"
          disabled={shares <= 0 || avgPrice <= 0}
        >
          Add Holding
        </Button>
        <Button variant="outlined" onClick={() => cancelHolding(true)}>
          Cancel
        </Button>
      </Box>
    </Box>
  );
}
