"use client";
import {
  EditHoldingData,
  EditHoldingRequest,
} from "@/types/portfolioHoldingsTypes";
import { EditPortfolioInfo } from "@/types/portfolioInfoTypes";
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
  rowDataOrPortfolio: EditHoldingData | EditPortfolioInfo;
  closeDialog: (data: boolean) => void;
  updatedHoldingData: (data: EditHoldingRequest | EditPortfolioInfo) => void;
};

export default function EditDialog({
  openDialog,
  rowDataOrPortfolio,
  closeDialog,
  updatedHoldingData,
}: EditDialogData) {
  const [open, setOpen] = useState(false);
  const [shares, setShares] = useState("");
  const [avgPrice, setAvgPrice] = useState("");
  const [portfolioName, setPortfolioName] = useState("");
  const [description, setDescription] = useState("");

  useEffect(() => {
    setOpen(openDialog);
    if (rowDataOrPortfolio.type === "holding") {
      setShares(rowDataOrPortfolio.shares.toString());
      setAvgPrice(rowDataOrPortfolio.avgPrice.toString());
    } else {
      setPortfolioName(rowDataOrPortfolio.portfolioName);
      setDescription(rowDataOrPortfolio.description);
    }
  }, [openDialog]);

  const handleClose = () => {
    setOpen(false);
    closeDialog(false);
  };

  const editTrim =
    rowDataOrPortfolio.type === "holding"
      ? shares.trim().length === 0 || avgPrice.trim().length === 0
      : portfolioName.trim().length === 0 ||
        (description?.trim() === "" && description.length !== 0);

  const hasChange =
    rowDataOrPortfolio.type === "holding"
      ? (shares !== rowDataOrPortfolio.shares.toString() && shares !== "0") ||
        (avgPrice !== rowDataOrPortfolio.avgPrice.toString() &&
          avgPrice !== "0")
      : portfolioName !== rowDataOrPortfolio.portfolioName ||
        description !== rowDataOrPortfolio.description;

  const handleSubmit = (e: React.SubmitEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (rowDataOrPortfolio.type === "holding") {
      updatedHoldingData({
        id: rowDataOrPortfolio.id,
        shares: Number(shares),
        avgPrice: Number(avgPrice),
        type: "holding",
      });
    } else {
      updatedHoldingData({
        id: rowDataOrPortfolio.id,
        portfolioName: portfolioName,
        description: description,
        type: "portfolio",
      });
    }
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
              {rowDataOrPortfolio.type === "holding"
                ? "Edit Holding"
                : "Edit Portfolio"}
            </Typography>
          </DialogTitle>
          <DialogContent>
            <DialogContentText>
              {rowDataOrPortfolio.type === "holding" && (
                <Box sx={{ display: "flex", gap: 2, pb: 4 }}>
                  <Typography variant="subtitle1" color="textPrimary">
                    Ticker: {rowDataOrPortfolio.ticker}
                  </Typography>
                  <Typography variant="subtitle1" color="textPrimary">
                    Company: {rowDataOrPortfolio.companyName}
                  </Typography>
                  <Typography variant="subtitle1" color="textPrimary">
                    Exchange: {rowDataOrPortfolio.exchange}
                  </Typography>
                </Box>
              )}
              {rowDataOrPortfolio.type === "portfolio" && (
                <Typography component="span" variant="body1">
                  Please fill out required fields
                </Typography>
              )}
            </DialogContentText>
            {rowDataOrPortfolio.type === "holding" && (
              <>
                <TextField
                  autoFocus
                  type="number"
                  required
                  margin="dense"
                  label="Shares"
                  id={`${rowDataOrPortfolio.id}-shares`}
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
                  id={`${rowDataOrPortfolio.id}-avgPrice`}
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
              </>
            )}
            {rowDataOrPortfolio.type === "portfolio" && (
              <>
                <TextField
                  autoFocus
                  required
                  margin="dense"
                  onChange={(e) => {
                    e.preventDefault();
                    setPortfolioName(e.target.value);
                  }}
                  id="portfolioName"
                  name="portfolioName"
                  label="Portfolio Name"
                  value={portfolioName}
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
                    e.preventDefault();
                    setDescription(e.target.value);
                  }}
                  id="portfolioDescription"
                  name="portfolioDescription"
                  value={description}
                  label="Portfolio Description"
                  fullWidth
                  variant="outlined"
                  rows={3}
                  slotProps={{
                    htmlInput: { maxLength: 250 },
                  }}
                />
              </>
            )}
          </DialogContent>
          <DialogActions>
            <Button onClick={handleClose}>Cancel</Button>
            <Button type="submit" disabled={!hasChange || editTrim}>
              Update
            </Button>
          </DialogActions>
        </Box>
      </Dialog>
    </React.Fragment>
  );
}
