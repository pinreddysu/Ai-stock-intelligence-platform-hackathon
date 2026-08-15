"use client";
import {
  columns,
  EditHoldingRequest,
  Holdings,
} from "@/types/portfolioHoldingsTypes";
import {
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableFooter,
  TableHead,
  TablePagination,
  TableRow,
} from "@mui/material";
import React from "react";
import { useState } from "react";
import Row from "./HoldingsTableRow";

type PaginationData = {
  pageData: (value: number) => void;
  sizeData: (value: number) => void;
  updatedHoldingData: (data: EditHoldingRequest) => void;
  deleteHolding: (data: number) => void;
  rows: Holdings[];
  totalData: number;
  pageNumber: number;
};

export default function PortfolioHoldingsStandardTable({
  pageData,
  sizeData,
  rows,
  totalData,
  pageNumber,
  updatedHoldingData,
  deleteHolding,
}: PaginationData) {
  const [rowsPerPage, setRowsPerPage] = useState(1);

  const handleChangePage = (
    event: React.MouseEvent<HTMLButtonElement> | null,
    newPage: number,
  ) => {
    pageData(newPage);
  };

  const handleChangeRowsPerPage = (
    event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>,
  ) => {
    setRowsPerPage(parseInt(event.target.value, 10));
    sizeData(parseInt(event.target.value, 10));
    pageData(0);
  };
  return (
    <TableContainer component={Paper} sx={{ gap: 2, display: "flex" }}>
      <Table aria-label="collapsible table">
        <TableHead>
          <TableRow>
            <TableCell
              key={"moreInfo"}
              variant="head"
              align={"left"}
              width={100}
              sx={{ backgroundColor: "background.paper" }}
            >
              More Details
            </TableCell>
            {columns.map((column) => (
              <TableCell
                key={column.dataKey}
                variant="head"
                align={column.numeric || false ? "right" : "left"}
                style={{ width: column.width }}
                sx={{ backgroundColor: "background.paper" }}
              >
                {column.label}
              </TableCell>
            ))}
            <TableCell
              key={"actions"}
              variant="head"
              align={"left"}
              width={100}
              sx={{ backgroundColor: "background.paper" }}
            >
              Actions
            </TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
            <Row
              key={row.ticker}
              row={row}
              updatedHoldingData={updatedHoldingData}
              deleteHolding={deleteHolding}
            />
          ))}
        </TableBody>
        <TableFooter>
          <TableRow>
            <TablePagination
              rowsPerPageOptions={[1, 5, 10, 25, 50]}
              count={totalData}
              rowsPerPage={rowsPerPage}
              page={pageNumber}
              onPageChange={handleChangePage}
              onRowsPerPageChange={handleChangeRowsPerPage}
            />
          </TableRow>
        </TableFooter>
      </Table>
    </TableContainer>
  );
}
