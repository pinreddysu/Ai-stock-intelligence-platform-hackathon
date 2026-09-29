import { Box, Typography } from "@mui/material";

export default function AddHoldingsPageHeader() {
  return (
    <Box
      sx={{
        display: "flex",
        width: "100%",
        justifyContent: "space-between",
        gap: 2,

        flexDirection: {
          xs: "column",
          sm: "row",
        },
      }}
    >
      <Box
        sx={{
          minWidth: 0,
          flex: 1,
        }}
      >
        <Typography
          variant="h3"
          sx={{
            overflowWrap: "break-word",
          }}
        >
          Add Holdings
        </Typography>

        <Typography
          variant="h6"
          sx={{
            mt: 1,
            overflowWrap: "break-word",
          }}
        >
          Add stocks and enter your purchase information
        </Typography>
      </Box>
    </Box>
  );
}
