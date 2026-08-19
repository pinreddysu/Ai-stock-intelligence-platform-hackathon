import { PortfolioAllocationData } from "@/types/portfolioHoldingsTypes";
import { Card, CardContent, CardHeader, Typography } from "@mui/material";
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
        <PieChart
          series={[
            {
              data: props.map((item) => ({
                id: item.sector,
                value: Number(item.allocationPercent.toFixed(2)),
                label: `${item.sector} (${Number(item.allocationPercent.toFixed(2))}%)`,
              })),
              innerRadius: 15,
              outerRadius: 80,
              paddingAngle: 1,
              cornerRadius: 5,
              startAngle: 0,
              endAngle: 360,
              highlightScope: { fade: "global", highlight: "item" },
              faded: { innerRadius: 30, additionalRadius: -30, color: "gray" },
            },
          ]}
          width={200}
          height={200}
        />
      </CardContent>
    </Card>
  );
}
