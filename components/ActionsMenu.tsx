"use client";
import { Button, Menu, MenuItem } from "@mui/material";
import React, { use, useEffect, useState } from "react";
import EditDialog from "./EditDialog";
import {
  EditHoldingData,
  EditHoldingRequest,
} from "@/types/portfolioHoldingsTypes";
import DeleteHoldingDialog from "./DeleteHoldingDialog";

type ActionMenu = {
  rowData: EditHoldingData;
  updatedHoldingData: (data: EditHoldingRequest) => void;
  deleteHolding: (data: number) => void;
};

export default function ActionMenu({
  rowData,
  updatedHoldingData,
  deleteHolding,
}: ActionMenu) {
  const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
  const open = Boolean(anchorEl);
  const [openDialog, setOpenDialog] = useState(false);
  const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
  const [deleteConfirmation, setDeleteConfirmation] = useState(0);
  const handleClick = (event: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(event.currentTarget);
  };
  const handleClose = () => {
    setAnchorEl(null);
  };

  const handleCloseOpenDialog = () => {
    setOpenDialog(true);
    setAnchorEl(null);
  };

  const handleCloseDelete = (id: number) => {
    setOpenDeleteDialog(true);
    setAnchorEl(null);
  };

  useEffect(() => {
    deleteHolding(deleteConfirmation);
  }, [deleteConfirmation]);

  return (
    <div>
      <Button
        id={`${rowData.id}-button`}
        aria-controls={open ? `${rowData.id}-menu` : undefined}
        aria-haspopup="true"
        aria-expanded={open}
        onClick={handleClick}
      >
        Actions
      </Button>
      <Menu
        id={`${rowData.id}-menu`}
        aria-labelledby={`${rowData.id}-button`}
        anchorEl={anchorEl}
        open={open}
        onClose={handleClose}
        anchorOrigin={{
          vertical: "top",
          horizontal: "left",
        }}
        transformOrigin={{
          vertical: "top",
          horizontal: "left",
        }}
      >
        <MenuItem onClick={handleCloseOpenDialog}>Edit Holding</MenuItem>
        <MenuItem onClick={() => handleCloseDelete(rowData.id)}>
          Delete Holding
        </MenuItem>
      </Menu>
      <EditDialog
        openDialog={openDialog}
        rowData={{
          id: rowData.id,
          ticker: rowData.ticker,
          companyName: rowData.companyName,
          exchange: rowData.exchange,
          shares: rowData.shares,
          avgPrice: rowData.avgPrice,
        }}
        closeDialog={setOpenDialog}
        updatedHoldingData={updatedHoldingData}
      />
      <DeleteHoldingDialog
        openDeleteDialog={openDeleteDialog}
        holdingData={{
          id: rowData.id,
          ticker: rowData.ticker,
          companyName: rowData.companyName,
        }}
        closeDialog={setOpenDeleteDialog}
        deleteConfirmation={setDeleteConfirmation}
      />
    </div>
  );
}
