"use client";

import {
  EditHoldingData,
  EditHoldingRequest,
} from "@/types/portfolioHoldingsTypes";
import {
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogContentText,
  TextField,
  DialogActions,
  Typography,
  Box,
  InputAdornment,
} from "@mui/material";
import React, { useEffect } from "react";
import { useState } from "react";

type EditDialogData = {
  openDialog: boolean;
  rowData: EditHoldingData;
  closeDialog: (data: boolean) => void;
  updatedHoldingData: (data: EditHoldingRequest) => void;
};

export default function EditDialog({
  openDialog,
  rowData,
  closeDialog,
  updatedHoldingData,
}: EditDialogData) {
  const [open, setOpen] = useState(false);
  const [shares, setShares] = useState("");
  const [avgPrice, setAvgPrice] = useState("");

  useEffect(() => {
    setOpen(openDialog);
    setShares(rowData.shares.toString());
    setAvgPrice(rowData.avgPrice.toString());
  }, [openDialog]);

  const handleClose = () => {
    setOpen(false);
    closeDialog(false);
  };

  const hasChange =
    (shares !== rowData.shares.toString() && shares !== "0") ||
    (avgPrice !== rowData.avgPrice.toString() && avgPrice !== "0");

  const handleSubmit = (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    updatedHoldingData({
      id: rowData.id,
      shares: Number(shares),
      avgPrice: Number(avgPrice),
    });
    handleClose();
  };

  return (
    <React.Fragment>
      <Dialog open={open} onClose={handleClose} maxWidth="sm" fullWidth>
        <Box component="form" onSubmit={handleSubmit}>
          <DialogTitle>
            <Typography
              component="span"
              variant="h5"
              sx={{ fontWeight: "bold" }}
            >
              Edit Holding
            </Typography>
          </DialogTitle>
          <DialogContent>
            <DialogContentText>
              <Box sx={{ display: "flex", gap: 2, pb: 4 }}>
                <Typography variant="subtitle1" color="textPrimary">
                  Ticker: {rowData.ticker}
                </Typography>
                <Typography variant="subtitle1" color="textPrimary">
                  Company: {rowData.companyName}
                </Typography>
                <Typography variant="subtitle1" color="textPrimary">
                  Exchange: {rowData.exchange}
                </Typography>
              </Box>
            </DialogContentText>
            <TextField
              autoFocus
              type="number"
              required
              margin="dense"
              label="Shares"
              id={`${rowData.id}-shares`}
              fullWidth
              value={shares}
              onChange={(e) => {
                e.preventDefault();
                setShares(e.target.value);
              }}
              variant="outlined"
              sx={{ pb: 3, display: "flex" }}
            />
            <TextField
              type="number"
              required
              margin="dense"
              label="Average Price"
              id={`${rowData.id}-avgPrice`}
              fullWidth
              variant="outlined"
              value={avgPrice}
              onChange={(e) => {
                e.preventDefault();
                setAvgPrice(e.target.value);
              }}
              slotProps={{
                input: {
                  startAdornment: (
                    <InputAdornment position="start">$</InputAdornment>
                  ),
                },
              }}
            />
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose}>Cancel</Button>
            <Button
              type="submit"
              disabled={
                !hasChange ||
                shares.trim().length === 0 ||
                avgPrice.trim().length === 0
              }
            >
              Update
            </Button>
          </DialogActions>
        </Box>
      </Dialog>
    </React.Fragment>
  );
}
