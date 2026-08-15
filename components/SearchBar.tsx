"use client";
import { Search } from "@mui/icons-material";
import { TextField } from "@mui/material";
import { useState } from "react";

type SearchProp = {
  page: string;
  searchData: (value: string) => void;
};

export default function SearchBar({ page, searchData }: SearchProp) {
  const [searchValue, setSearchValue] = useState("");

  return (
    <TextField
      variant="outlined"
      size="small"
      id="searchPortfolio"
      name="searchPortfolio"
      value={searchValue}
      sx={{ width: { sm: "50%", xs: "100%" } }}
      placeholder={`Search ${page === "addHoldings" ? "stocks" : "holdings"}`}
      onChange={(e) => {
        setSearchValue(e.target.value);
        searchData(e.target.value);
      }}
      slotProps={{
        input: {
          startAdornment: <Search />,
        },
        htmlInput: {
          maxLength: 50,
        },
      }}
    ></TextField>
  );
}
