import { PortfolioAllocationData } from "@/types/portfolioHoldingsTypes";
import { Box, Card, CardContent, CardHeader, Typography } from "@mui/material";
import { PieChart } from "@mui/x-charts";

export default function AllocationChart({
  props,
}: {
  props: PortfolioAllocationData[];
}) {
  return (
    <Card sx={{ width: { xs: "100%", sm: "50%" }, boxShadow: 8 }}>
      <CardHeader
        title={
          <Typography sx={{ fontWeight: "bold" }}>
            Sector Allocation %
          </Typography>
        }
      />
      <CardContent>
        <Box sx={{ width: "100%", height: 400 }}>
          <PieChart
            series={[
              {
                data: props.map((item) => ({
                  id: item.sector,
                  value: Number(item.allocationPercent.toFixed(2)),
                  label: `${item.sector} (${Number(item.allocationPercent.toFixed(2))}%)`,
                })),
                innerRadius: 15,
                outerRadius: 140,
                paddingAngle: 1,
                cornerRadius: 5,
                startAngle: 0,
                endAngle: 360,
                highlightScope: { fade: "global", highlight: "item" },
                faded: {
                  innerRadius: 30,
                  additionalRadius: -30,
                  color: "gray",
                },
              },
            ]}
            slotProps={{
              legend: {
                direction: "horizontal",
                position: { vertical: "bottom", horizontal: "center" },
              },
            }}
          />
        </Box>
      </CardContent>
    </Card>
  );
}
