"use client";
import {
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Button,
  Typography,
  Box,
} from "@mui/material";
import { useEffect, useState } from "react";

interface HoldingData {
  id: number;
  ticker: string;
  companyName: string;
  exchange: string;
}

type DeleteDialogProps = {
  openDeleteDialog: boolean;
  holdingData: HoldingData;
  deleteConfirmation: (data: number) => void;
  closeDialog: (data: boolean) => void;
};
export default function DeleteHoldingDialog({
  openDeleteDialog,
  holdingData,
  deleteConfirmation,
  closeDialog,
}: DeleteDialogProps) {
  const [open, setOpen] = useState(false);

  const handleClose = () => {
    setOpen(false);
    closeDialog(false);
  };

  const handleDelete = () => {
    deleteConfirmation(holdingData.id);
    handleClose();
  };

  useEffect(() => {
    setOpen(openDeleteDialog);
  }, [openDeleteDialog]);

  return (
    <>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby={`alert-dialog-title-${holdingData.id}`}
        aria-describedby={`alert-dialog-description-${holdingData.id}`}
        role="alertdialog"
      >
        <DialogTitle id={`${holdingData.id}-deleteHolding`}>
          <Typography component="span" variant="h5" sx={{ fontWeight: "bold" }}>
            Delete Holding
          </Typography>
        </DialogTitle>
        <DialogContent>
          <Box
            key={holdingData.id}
            sx={{ display: "flex", pb: 4, flexDirection: "column" }}
          >
            <Typography variant="subtitle1" color="textPrimary">
              Are you sure you want to delete this holding?
            </Typography>
            <Typography variant="subtitle1" color="textPrimary">
              Ticker: {holdingData.ticker}
            </Typography>
            <Typography variant="subtitle1" color="textPrimary">
              Company: {holdingData.companyName}
            </Typography>
            <Typography variant="subtitle1" color="textPrimary">
              Exchange: {holdingData.exchange}
            </Typography>
          </Box>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} autoFocus>
            Cancel
          </Button>
          <Button onClick={handleDelete}>Delete</Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
