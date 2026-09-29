"use client";
import AddHoldingsPageHeader from "@/components/AddHoldingsPageHeader";
import BackPageRedirection from "@/components/BackPageRedirection";
import Logo from "@/components/Logo";
import { stocksService } from "@/services/stocksService";
import {
  AddHoldingsData,
  StockListData,
  StockLists,
  StockMoreDetails,
} from "@/types/stocksTypes";
import {
  Alert,
  AppBar,
  Box,
  Button,
  CircularProgress,
  Container,
  Snackbar,
  SnackbarCloseReason,
  Toolbar,
} from "@mui/material";
import { useParams, useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import React from "react";
import ShowMoreDetailsStocks from "@/components/ShowMoreDetailsStocks";
import portfolioHoldingService, {
  AddPortfolioHoldingRequest,
} from "@/services/portfolioHoldingService";
import LeftCardAddHoldings from "@/components/LeftCardAddHoldings";
import RightCardAddHoldings from "@/components/RightCardAddHoldings";

export default function AddHoldings() {
  const params = useParams();
  const portfolioId = Number(params.id);
  const [searchValue, setSearchValue] = useState("");
  const [stocksList, setStocksList] = useState<StockListData>();
  const [stockMoreDetails, setStockMoreDetails] = useState<StockMoreDetails>();
  const [page, setPage] = useState(0);
  const [selectedStocks, setSelectedStocks] = useState<StockLists>();
  const [isLoading, setIsLoading] = useState(true);
  const [openMoreDetails, setOpenMoreDetails] = useState(false);
  const [cancelHolding, setCancelHolding] = useState(false);
  const [holdingData, setHoldingData] = useState<AddHoldingsData>();
  const [holdingDataListPending, setHoldingDataListPending] = useState<
    AddHoldingsData[]
  >([]);
  const [holdingDataList, setHoldingDataList] = useState<
    AddPortfolioHoldingRequest[]
  >([]);
  const [disableIds, setDisableIds] = useState<number[]>([]);
  const [removeDisabledId, setRemoveDisabledId] = useState(0);
  const [openSnackBar, setOpenSnackBar] = useState("");
  const [initialLoading, setIntialLoading] = useState(true);
  const router = useRouter();

  const handleChangePage = (
    event: React.ChangeEvent<unknown>,
    page: number,
  ) => {
    setPage(page - 1);
  };

  const handleCloseSnackBar = (
    event: React.SyntheticEvent | Event,
    reason?: SnackbarCloseReason,
  ) => {
    if (reason === "clickaway") {
      return;
    }

    setOpenSnackBar("");
  };

  const getStocks = async (
    portfolioId: number,
    keyword: string,
    page: number,
  ) => {
    setIsLoading(true);
    try {
      const response = await stocksService.getBasicStocksInfo(
        keyword,
        page,
        portfolioId,
      );
      setStocksList(response.data);
      setIntialLoading(false);
    } catch (error: any) {
      if (error.response?.status === 401 || error.code === "ERR_NETWORK") {
        localStorage.removeItem("token");
        router.replace("/login");
      } else {
        console.error("Failed to fetch portfolios", error);
      }
    } finally {
      setIsLoading(false);
    }
  };

  const getStockMoreDetails = async (openMoreDetails: string) => {
    try {
      const response = await stocksService.getStockMoreDetails(openMoreDetails);
      setStockMoreDetails(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleMoreStocks = (ticker: string) => {
    getStockMoreDetails(ticker);
    setOpenMoreDetails(true);
  };

  useEffect(() => {
    const timer = setTimeout(() => {
      getStocks(portfolioId, searchValue, page);
    }, 600);
    return () => clearTimeout(timer);
  }, [searchValue, page]);

  const handleAddHolding = (data: StockLists) => {
    setSelectedStocks(data);
  };

  useEffect(() => {
    setSelectedStocks(undefined);
    setCancelHolding(false);
  }, [cancelHolding]);

  useEffect(() => {
    if (holdingData) {
      setHoldingDataListPending((prevList) => [...prevList, holdingData]);
      setDisableIds((prevList) => [...prevList, holdingData.stockId]);
    }
  }, [holdingData]);

  useEffect(() => {
    setHoldingDataList(holdingDataListPending);
  }, [holdingDataListPending]);

  useEffect(() => {
    console.log(removeDisabledId);
    setDisableIds(disableIds.filter((x) => x !== removeDisabledId));
  }, [removeDisabledId]);

  const addHoldingsList = async () => {
    try {
      const response =
        await portfolioHoldingService.addHoldingsList(holdingDataList);
      setOpenSnackBar(response.data);
      router.push(`/portfolioDetails/${portfolioId}`);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <>
      {initialLoading && (
        <Box
          sx={{
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
            width: "100vw",
            height: "100vh",
          }}
        >
          <CircularProgress />
        </Box>
      )}
      {initialLoading === false && (
        <Box>
          <AppBar position="static">
            <Toolbar>
              <Logo />
            </Toolbar>
          </AppBar>
          <Container maxWidth="xl">
            <Box
              sx={{
                py: 4,
                pb: 4,
                display: "flex",
                alignItems: "center",
              }}
            >
              <BackPageRedirection
                pageName="addHoldings"
                route={`portfolioDetails/${portfolioId}`}
              />
            </Box>
            <AddHoldingsPageHeader />
            <Box
              sx={{
                display: "flex",
                flexDirection: { sm: "row", xs: "column" },
                gap: 4,
                pt: 4,
                justifyContent: { xs: "flex-start" },
                pb: 6,
                maxHeight: 900,
              }}
            >
              <LeftCardAddHoldings
                setSearchValue={setSearchValue}
                setCancelHolding={setCancelHolding}
                setHoldingData={setHoldingData}
                handleChangePage={handleChangePage}
                handleMoreStocks={handleMoreStocks}
                handleAddHolding={handleAddHolding}
                searchValue={searchValue}
                stocksList={stocksList}
                isLoading={isLoading}
                page={page}
                selectedStocks={selectedStocks}
                disableIds={disableIds}
              />
              <RightCardAddHoldings
                holdingDataList={holdingDataListPending}
                pendingHoldingDataList={setHoldingDataListPending}
                removePendingListItemId={setRemoveDisabledId}
              />
            </Box>
            {stockMoreDetails && (
              <ShowMoreDetailsStocks
                openMoreDetails={openMoreDetails}
                closeMoreDetails={setOpenMoreDetails}
                stockMoreDetails={stockMoreDetails}
              />
            )}
            <Snackbar
              anchorOrigin={{ horizontal: "center", vertical: "bottom" }}
              open={openSnackBar !== ""}
              autoHideDuration={3000}
              onClose={handleCloseSnackBar}
            >
              <Alert
                onClose={handleCloseSnackBar}
                severity="success"
                variant="filled"
                sx={{ width: "100%" }}
              >
                {openSnackBar}
              </Alert>
            </Snackbar>
            <Box sx={{ display: "flex", justifyContent: "center" }}>
              <Button
                variant="contained"
                onClick={() => addHoldingsList()}
                disabled={holdingDataList.length === 0}
              >
                Save Holdings
              </Button>
            </Box>
          </Container>
        </Box>
      )}
    </>
  );
}
