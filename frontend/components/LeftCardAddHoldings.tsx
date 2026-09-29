"use client";
import {
  Card,
  CardContent,
  Box,
  Typography,
  List,
  Skeleton,
  Divider,
  ListItem,
  ListItemText,
  Tooltip,
  IconButton,
  Pagination,
} from "@mui/material";
import AddHoldingForm from "./AddHoldingForm";
import SearchBar from "./SearchBar";
import {
  AddHoldingsData,
  StockListData,
  StockLists,
} from "@/types/stocksTypes";
import AddOutlinedIcon from "@mui/icons-material/AddOutlined";
import MoreVertOutlinedIcon from "@mui/icons-material/MoreVertOutlined";

type LeftCardAddHoldingsProps = {
  setSearchValue: (data: string) => void;
  setCancelHolding: (data: boolean) => void;
  searchValue: string;
  stocksList: StockListData | undefined;
  isLoading: boolean;
  page: number;
  selectedStocks: StockLists | undefined;
  disableIds: number[];
  setHoldingData: (data: AddHoldingsData) => void;
  handleChangePage: (
    event: React.ChangeEvent<unknown, Element>,
    page: number,
  ) => void;
  handleMoreStocks: (ticker: string) => void;
  handleAddHolding: (data: StockLists) => void;
};

export default function LeftCardAddHoldings({
  setSearchValue,
  setCancelHolding,
  setHoldingData,
  handleChangePage,
  handleMoreStocks,
  handleAddHolding,
  searchValue,
  stocksList,
  isLoading,
  page,
  selectedStocks,
  disableIds,
}: LeftCardAddHoldingsProps) {
  return (
    <Card sx={{ width: { xs: "100%", sm: "50%" }, overflow: "auto" }}>
      <CardContent>
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            gap: 3,
            px: 2,
            pb: 2,
            width: "100%",
          }}
        >
          <Box sx={{ pt: 2 }}>
            <Typography variant="h5">Add Stocks</Typography>
          </Box>
          <Box sx={{ display: "flex", gap: 1, flexDirection: "column" }}>
            <Typography variant="body1">Search Stocks</Typography>
            <SearchBar
              searchData={setSearchValue}
              disabled={false}
              page="addHoldings"
            />
          </Box>
          <Box>
            <Box sx={{ display: "flex", gap: 2, width: "100%" }}>
              {searchValue.trim().length === 0 && (
                <Box sx={{ display: "flex", justifyContent: "center" }}>
                  <Typography variant="h4" color="textDisabled">
                    Search to get started
                  </Typography>
                </Box>
              )}
              {searchValue.trim().length !== 0 && (
                <Box sx={{ width: "100%" }}>
                  {/* <Typography variant="body1">Select Stock</Typography> */}
                  <List
                    sx={{ width: "100%", maxHeight: 300, overflow: "auto" }}
                  >
                    <Typography variant="h6" sx={{ display: "flex", pb: 1 }}>
                      Select a stock to add in holdings
                    </Typography>
                    {stocksList?.totalElements === 0 && (
                      <Box>
                        <Typography
                          variant="caption"
                          sx={{ fontStyle: "italic" }}
                        >
                          No results found
                        </Typography>
                      </Box>
                    )}
                    {isLoading && (
                      <Box sx={{ display: "flex", justifyContent: "center" }}>
                        <Skeleton variant="rounded" width="100%" height={100} />
                      </Box>
                    )}
                    {stocksList &&
                      !isLoading &&
                      stocksList.stockLists.map((item) => (
                        <Box key={item.id}>
                          <Divider />
                          <ListItem key={item.id}>
                            <ListItemText
                              primary={item.ticker}
                              secondary={item.companyName}
                            />
                            <Tooltip title="Add">
                              <IconButton
                                disabled={disableIds.includes(item.id)}
                                onClick={() => {
                                  handleAddHolding({
                                    id: item.id,
                                    ticker: item.ticker,
                                    companyName: item.companyName,
                                  });
                                }}
                              >
                                <AddOutlinedIcon />
                              </IconButton>
                            </Tooltip>
                            <Tooltip title="More Details">
                              <IconButton
                                onClick={() => handleMoreStocks(item.ticker)}
                              >
                                <MoreVertOutlinedIcon />
                              </IconButton>
                            </Tooltip>
                          </ListItem>
                          <Divider />
                        </Box>
                      ))}
                  </List>
                  <Box
                    sx={{
                      display: "flex",
                      justifyContent: "center",
                      pt: 2,
                    }}
                  >
                    {!isLoading && (
                      <Pagination
                        count={
                          stocksList?.totalElements
                            ? Math.ceil(stocksList.totalElements / 5)
                            : 0
                        }
                        page={page + 1}
                        variant="outlined"
                        shape="rounded"
                        siblingCount={0}
                        color="primary"
                        onChange={handleChangePage}
                      />
                    )}
                  </Box>
                  {selectedStocks && (
                    <AddHoldingForm
                      stockData={selectedStocks}
                      cancelHolding={setCancelHolding}
                      addHoldingData={setHoldingData}
                    />
                  )}
                </Box>
              )}
            </Box>
          </Box>
        </Box>
      </CardContent>
    </Card>
  );
}
