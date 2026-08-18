"use client";
import { StockMoreDetails } from "@/types/stocksTypes";
import CloseOutlinedIcon from "@mui/icons-material/CloseOutlined";
import {
  Box,
  Dialog,
  DialogContent,
  DialogContentText,
  DialogTitle,
  IconButton,
  Typography,
} from "@mui/material";
import { useEffect, useState } from "react";

type ShowMoreDetailsStocks = {
  openMoreDetails: boolean;
  stockMoreDetails: StockMoreDetails;
  closeMoreDetails: (data: boolean) => void;
};
export default function ShowMoreDetailsStocks({
  openMoreDetails,
  stockMoreDetails,
  closeMoreDetails,
}: ShowMoreDetailsStocks) {
  const [openDialog, setOpenDialog] = useState(false);
  const handleCloseDialog = () => {
    setOpenDialog(false);
    closeMoreDetails(false);
  };

  useEffect(() => {
    setOpenDialog(openMoreDetails);
  }, [openMoreDetails]);
  return (
    <>
      <Dialog
        open={openDialog}
        onClose={handleCloseDialog}
        maxWidth="sm"
        fullWidth
      >
        <DialogTitle>
          <Box
            sx={{
              display: "flex",
              justifyContent: "space-between",
            }}
          >
            <Typography
              component="span"
              variant="h5"
              sx={{ fontWeight: "bold", alignItems: "center" }}
            >
              More Details
            </Typography>
            <IconButton onClick={() => handleCloseDialog()}>
              <CloseOutlinedIcon />
            </IconButton>
          </Box>
        </DialogTitle>
        <DialogContent>
          <DialogContentText>
            <Box sx={{ display: "flex", gap: 0.5, pb: 4, flexDirection: "column" }}>
              <Typography variant="subtitle1" color="textPrimary">
                Ticker: {stockMoreDetails.ticker}
              </Typography>
              <Typography variant="subtitle1" color="textPrimary">
                Company Name: {stockMoreDetails.companyName}
              </Typography>
              <Typography variant="subtitle1" color="textPrimary">
                Exchange: {stockMoreDetails.exchange}
              </Typography>
              <Typography variant="subtitle1" color="textPrimary">
                Sector: {stockMoreDetails.sector}
              </Typography>
              <Typography variant="subtitle1" color="textPrimary">
                Industry: {stockMoreDetails.industry}
              </Typography>
              <Typography variant="subtitle1" color="textPrimary">
                Current Price: {stockMoreDetails.prices}
              </Typography>
              <Typography
                variant="subtitle1"
                color={stockMoreDetails.change > 0 ? "success" : "error"}
              >
                Change: {stockMoreDetails.change.toFixed(2)}
              </Typography>
              <Typography
                variant="subtitle1"
                color={stockMoreDetails.changePercent > 0 ? "success" : "error"}
              >
                Change %: {stockMoreDetails.changePercent.toFixed(2)}%
              </Typography>
              <Typography variant="subtitle1" color="textPrimary">
                Previous Close: {stockMoreDetails.previousClose}
              </Typography>
              <Typography
                variant="subtitle1"
                color="textPrimary"
                sx={{ fontStyle: "italic" }}
              >
                Market Timestamp: {stockMoreDetails.marketTimeStamp}
              </Typography>
              <Typography
                variant="subtitle1"
                color="textPrimary"
                sx={{ fontStyle: "italic" }}
              >
                Last Updated At: {stockMoreDetails.lastUpdatedAt}
              </Typography>
            </Box>
          </DialogContentText>
        </DialogContent>
      </Dialog>
    </>
  );
}
