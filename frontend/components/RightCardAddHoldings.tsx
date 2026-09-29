"use client";
import { AddHoldingsData } from "@/types/stocksTypes";
import {
  Box,
  Card,
  CardContent,
  Divider,
  IconButton,
  List,
  ListItem,
  ListItemText,
  Tooltip,
  Typography,
} from "@mui/material";
import RemoveCircleOutlineOutlinedIcon from "@mui/icons-material/RemoveCircleOutlineOutlined";
import { useEffect, useState } from "react";

type RightCardAddHoldingsProps = {
  holdingDataList: AddHoldingsData[];
  pendingHoldingDataList: (data: AddHoldingsData[]) => void;
  removePendingListItemId: (data: number) => void;
};

export default function RightCardAddHoldings({
  holdingDataList,
  pendingHoldingDataList,
  removePendingListItemId,
}: RightCardAddHoldingsProps) {
  const [holdingList, setHoldingList] = useState<AddHoldingsData[]>([]);
  const [removeId, setRemoveId] = useState(0);

  useEffect(() => {
    const updatedHoldingDataList = holdingList.filter(
      (holding) => holding.stockId !== removeId,
    );
    setHoldingList(updatedHoldingDataList);
    pendingHoldingDataList(updatedHoldingDataList);
    removePendingListItemId(removeId);
  }, [removeId]);

  useEffect(() => {
    setHoldingList(holdingDataList);
  }, [holdingDataList]);

  return (
    <Card sx={{ width: { sx: "100%", sm: "50%" } }}>
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
            <Typography variant="h5">Holdings to Add</Typography>
          </Box>
          {holdingList.length !== 0 ? (
            <List
              sx={{
                width: "100%",
                maxHeight: "100%",
                position: "relative",
                overflow: "auto",
              }}
            >
              <Divider />
              {holdingList.map((item) => (
                <Box key={item.stockId}>
                  <ListItem key={item.stockId}>
                    <ListItemText
                      primary={`${item.companyName} (${item.ticker})`}
                      secondary={
                        <Box component="span">
                          <Typography
                            variant="body2"
                            component="span"
                          >{`${item.shares.toFixed(2)} ${item.shares === 1 ? "share" : "shares"} · $${item.avgPrice.toFixed(2)}`}</Typography>
                        </Box>
                      }
                    />
                    <Tooltip title="Remove">
                      <IconButton
                        onClick={() => {
                          setRemoveId(item.stockId);
                        }}
                      >
                        <RemoveCircleOutlineOutlinedIcon />
                      </IconButton>
                    </Tooltip>
                  </ListItem>
                  <Divider />
                </Box>
              ))}
            </List>
          ) : (
            <Box>
              <Box>
                <Typography sx={{ fontStyle: "italic" }}>
                  Select a stock from the left to get started
                </Typography>
              </Box>
              <Box sx={{ display: "flex", justifyContent: "center", pt: 3 }}>
                <Typography variant="h4" color="textDisabled">
                  No holdings added yet
                </Typography>
              </Box>
            </Box>
          )}
        </Box>
      </CardContent>
    </Card>
  );
}
