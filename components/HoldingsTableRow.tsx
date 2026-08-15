"use client";
import {
  columns,
  EditHoldingRequest,
  Holdings,
} from "@/types/portfolioHoldingsTypes";
import { TableRow, TableCell, IconButton, Collapse } from "@mui/material";
import React, { useState } from "react";
import ActionsMenu from "./ActionsMenu";
import MoreDetails from "./MoreDetails";
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";
import KeyboardArrowUpIcon from "@mui/icons-material/KeyboardArrowUp";

type Row = {
  row: Holdings;
  updatedHoldingData: (data: EditHoldingRequest) => void;
  deleteHolding: (data: number) => void;
};

export default function Row({ row, updatedHoldingData, deleteHolding }: Row) {
  const [openTicker, setOpenTicker] = useState<String | null>(null);
  const isOpen = openTicker === row.ticker;
  return (
    <React.Fragment>
      <TableRow key={row.holdingId}>
        <TableCell>
          <IconButton
            aria-label="expand row"
            size="small"
            onClick={() => setOpenTicker(isOpen ? null : row.ticker)}
          >
            {isOpen ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
          </IconButton>
        </TableCell>
        {columns.map((column) => (
          <TableCell
            key={column.dataKey}
            align={column.numeric || false ? "right" : "left"}
            sx={{
              verticalAlign: "center",
            }}
          >
            {column.dataKey === "allocation" ||
            column.dataKey === "gainOrLossPercent"
              ? `${row[column.dataKey].toFixed(2)}%`
              : column.dataKey === "avgPrice" ||
                  column.dataKey === "currentValue" ||
                  column.dataKey === "price"
                ? `$${row[column.dataKey].toFixed(2)}`
                : column.dataKey === "shares"
                  ? row[column.dataKey].toFixed(2)
                  : row[column.dataKey]}
          </TableCell>
        ))}
        <TableCell key={"actions"} variant="head" align={"left"} width={100}>
          <ActionsMenu
            rowData={{
              id: row.holdingId,
              ticker: row.ticker,
              companyName: row.companyName,
              exchange: row.exchange,
              shares: row.shares,
              avgPrice: row.avgPrice,
            }}
            updatedHoldingData={updatedHoldingData}
            deleteHolding={deleteHolding}
          />
        </TableCell>
      </TableRow>
      <TableRow>
        <TableCell
          style={{ paddingBottom: 0, paddingTop: 0 }}
          colSpan={columns.length + 2}
        >
          <Collapse unmountOnExit in={isOpen} timeout="auto">
            <MoreDetails props={row} />
          </Collapse>
        </TableCell>
      </TableRow>
    </React.Fragment>
  );
}
