"use client";
import { DeletePortfolioInfo } from "@/types/portfolioInfoTypes";
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
  type: "holdings";
}

type DeleteDialogProps = {
  openDeleteDialog: boolean;
  holdingDataOrPortfolio: HoldingData | DeletePortfolioInfo;
  deleteConfirmation: (data: number) => void;
  closeDialog: (data: boolean) => void;
};

export default function DeleteDialog({
  openDeleteDialog,
  holdingDataOrPortfolio,
  deleteConfirmation,
  closeDialog,
}: DeleteDialogProps) {
  const [open, setOpen] = useState(false);

  const handleClose = () => {
    setOpen(false);
    closeDialog(false);
  };

  const handleDelete = () => {
    deleteConfirmation(holdingDataOrPortfolio.id);
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
        aria-labelledby={`alert-dialog-title-${holdingDataOrPortfolio.type === "holdings" ? holdingDataOrPortfolio.id : holdingDataOrPortfolio.id}`}
        aria-describedby={`alert-dialog-description-${holdingDataOrPortfolio.type === "holdings" ? holdingDataOrPortfolio.id : holdingDataOrPortfolio.id}`}
        role="alertdialog"
      >
        <DialogTitle
          id={`${holdingDataOrPortfolio.type === "holdings" ? holdingDataOrPortfolio.id : holdingDataOrPortfolio.id}-deleteHolding`}
        >
          <Typography component="span" variant="h5" sx={{ fontWeight: "bold" }}>
            Delete Holding
          </Typography>
        </DialogTitle>
        <DialogContent>
          <Box
            key={
              holdingDataOrPortfolio.type === "holdings"
                ? holdingDataOrPortfolio.id
                : holdingDataOrPortfolio.id
            }
            sx={{ display: "flex", pb: 4, flexDirection: "column" }}
          >
            <Typography variant="subtitle1" color="textPrimary">
              {`Are you sure you want to delete this ${holdingDataOrPortfolio.type}?`}
            </Typography>
            {holdingDataOrPortfolio.type === "holdings" && (
              <>
                <Typography variant="subtitle1" color="textPrimary">
                  Ticker: {holdingDataOrPortfolio.ticker}
                </Typography>
                <Typography variant="subtitle1" color="textPrimary">
                  Company: {holdingDataOrPortfolio.companyName}
                </Typography>
                <Typography variant="subtitle1" color="textPrimary">
                  Exchange: {holdingDataOrPortfolio.exchange}
                </Typography>
              </>
            )}
            {holdingDataOrPortfolio.type === "portfolio" && (
              <>
                <Typography variant="subtitle1" color="textPrimary">
                  Portfolio: {holdingDataOrPortfolio.portfolioName}
                </Typography>
                {holdingDataOrPortfolio.description?.length !== 0 &&
                  holdingDataOrPortfolio.description.trim() !== "" && (
                    <Typography variant="subtitle1" color="textPrimary">
                      Portfolio Description:{" "}
                      {holdingDataOrPortfolio.description}
                    </Typography>
                  )}
              </>
            )}
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
