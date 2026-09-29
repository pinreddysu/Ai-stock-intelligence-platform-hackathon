import { Box, Button } from "@mui/material";
import { useRouter } from "next/navigation";
import ArrowBackOutlinedIcon from "@mui/icons-material/ArrowBackOutlined";

type PortfolioPage = {
  pageName: string;
  route: string;
};

export default function BackPageRedirection({
  pageName,
  route,
}: PortfolioPage) {
  const router = useRouter();
  return (
    <Box>
      {pageName === "portfolioHoldings" && (
        <Button
          variant="outlined"
          size="small"
          onClick={() => router.replace(`/${route}`)}
          startIcon={<ArrowBackOutlinedIcon />}
        >
          Back To Portfolio List
        </Button>
      )}
      {pageName === "addHoldings" && (
        <Button
          variant="outlined"
          size="small"
          onClick={() => router.replace(`/${route}`)}
          startIcon={<ArrowBackOutlinedIcon />}
        >
          Back To Portfolio Holdings
        </Button>
      )}
    </Box>
  );
}
