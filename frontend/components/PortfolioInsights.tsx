import {
  MaxStockAllocationData,
  PortfolioAllocationData,
} from "@/types/portfolioHoldingsTypes";
import {
  Alert,
  Box,
  Card,
  CardContent,
  CardHeader,
  Typography,
} from "@mui/material";

type PortfolioInsightsProps = {
  maxAllocationData: MaxStockAllocationData[];
  sectorAllocationData: PortfolioAllocationData[];
};

export default function PortfolioInsights({
  maxAllocationData,
  sectorAllocationData,
}: PortfolioInsightsProps) {
  const sectorConcentration = sectorAllocationData.filter(
    (item) => item.allocationPercent > 60,
  );
  const allocationConcentration = maxAllocationData.filter(
    (item) => item.allocationPercent > 40,
  );
  const maxPerformance = maxAllocationData[0];
  const holdingsCount = maxAllocationData.length;
  const sectorCount = sectorAllocationData.length;
  const minPerformance = maxAllocationData[holdingsCount - 1];
  const positivePerformanceHoldingCount = maxAllocationData.filter(
    (item) => item.stockPerformance > 0,
  ).length;
  const negativePerformanceHoldingCount =
    holdingsCount - positivePerformanceHoldingCount;

  return (
    <Card sx={{ width: { xs: "100%", sm: "50%" }, boxShadow: 8 }}>
      <CardHeader
        title={
          <Typography sx={{ fontWeight: "bold" }}>
            Portfolio Insights
          </Typography>
        }
      />
      <CardContent>
        <Box>
          {sectorConcentration.length !== 0 && (
            <Box sx={{ pb: { xs: 1, lg: 4 } }}>
              <Alert severity="warning">
                {`${
                  sectorConcentration[0].sector.charAt(0).toUpperCase() +
                  sectorConcentration[0].sector.toLowerCase().slice(1)
                } represents ${sectorConcentration[0].allocationPercent.toFixed(2)}%`}
              </Alert>
            </Box>
          )}
          {allocationConcentration.length !== 0 &&
            allocationConcentration.map((item) => (
              <Box sx={{ pb: { xs: 1, lg: 4 } }}>
                <Alert severity="warning">{`${item.ticker} represents ${item.allocationPercent.toFixed(2)}%`}</Alert>
              </Box>
            ))}
          {maxPerformance && (
            <Box sx={{ pb: { xs: 1, lg: 4 } }}>
              <Alert severity="info">{`${maxPerformance.ticker} is your best-performing, with a gain of ${maxPerformance.stockPerformance.toFixed(2)}%`}</Alert>
            </Box>
          )}
          {minPerformance && (
            <Box sx={{ pb: { xs: 1, lg: 4 } }}>
              <Alert
                severity={
                  minPerformance.stockPerformance > 0 ? "info" : "warning"
                }
              >{`${minPerformance.ticker} is your lowest-performing holding, with a ${minPerformance.stockPerformance > 0 ? "gain" : "loss"} of ${minPerformance.stockPerformance.toFixed(2)}%`}</Alert>
            </Box>
          )}
          <Box sx={{ pb: { xs: 1, lg: 4 } }}>
            <Alert
              severity={holdingsCount >= 5 ? "success" : "warning"}
            >{`Your portfolio contains ${holdingsCount} holdings across ${sectorCount !== 1 ? `${sectorCount} sectors` : `${sectorCount} sector`}`}</Alert>
          </Box>
          <Box sx={{ pb: { xs: 1, lg: 4 } }}>
            <Alert
              severity={
                negativePerformanceHoldingCount === 0
                  ? "success"
                  : positivePerformanceHoldingCount >=
                      negativePerformanceHoldingCount
                    ? "info"
                    : "warning"
              }
            >{`${
              negativePerformanceHoldingCount === 0
                ? `All ${holdingsCount} of your holdings are currently above their average purchase price`
                : `${positivePerformanceHoldingCount} of ${holdingsCount} holdings are above their average purchase price, 
                while ${negativePerformanceHoldingCount} are below`
            }`}</Alert>
          </Box>
        </Box>
      </CardContent>
    </Card>
  );
}
