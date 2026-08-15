"use client";
import { EditPortfolioInfo, PortfolioInfo } from "@/types/portfolioInfoTypes";
import { Box, Button, Typography } from "@mui/material";
import DeleteDialog from "./DeleteDialog";
import { useState } from "react";
import EditDialog from "./EditDialog";
import { EditHoldingRequest } from "@/types/portfolioHoldingsTypes";

type PortfolioHoldingHeader = {
  portfolioInfo: PortfolioInfo;
  deletePortfolio: (data: number) => void;
  editPortfolio: (data: EditHoldingRequest | EditPortfolioInfo) => void;
};

export default function PortfolioHoldingHeader({
  portfolioInfo,
  deletePortfolio,
  editPortfolio,
}: PortfolioHoldingHeader) {
  const [openDeleteDialog, setOpenDeleteDialog] = useState(false);
  const [openEditDialog, setOpenEditDialog] = useState(false);

  const handleEdit = () => {
    setOpenEditDialog(true);
  };

  const handleDelete = () => {
    setOpenDeleteDialog(true);
  };

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
          {portfolioInfo.portfolioName}
        </Typography>

        <Typography
          variant="h6"
          sx={{
            mt: 1,
            overflowWrap: "break-word",
          }}
        >
          {portfolioInfo.description}
        </Typography>

        <Typography
          variant="body1"
          sx={{
            mt: 0.5,
            fontStyle: "italic",
          }}
        >
          {portfolioInfo.portfolioHoldings === 1
            ? `Total Holdings: ${portfolioInfo.portfolioHoldings}`
            : `Total Holding: ${portfolioInfo.portfolioHoldings}`}
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
        <Button variant="outlined" onClick={() => handleEdit()}>
          Edit
        </Button>
        <Button variant="outlined" color="error" onClick={() => handleDelete()}>
          Delete
        </Button>
      </Box>
      <DeleteDialog
        openDeleteDialog={openDeleteDialog}
        closeDialog={setOpenDeleteDialog}
        deleteConfirmation={deletePortfolio}
        holdingDataOrPortfolio={{
          id: portfolioInfo.id,
          portfolioName: portfolioInfo.portfolioName,
          description: portfolioInfo.description,
          type: "portfolio",
        }}
      />
      <EditDialog
        openDialog={openEditDialog}
        rowDataOrPortfolio={{
          id: portfolioInfo.id,
          portfolioName: portfolioInfo.portfolioName,
          description: portfolioInfo.description,
          type: "portfolio",
        }}
        closeDialog={setOpenEditDialog}
        updatedHoldingData={editPortfolio}
      />
    </Box>
  );
}
