"use client";
import WelcomePage from "@/components/WelcomPage";
export default function CreateAccountPage() {
  const prop = {
    firstName: true,
    lastName: true,
    title: "Get Started",
    description: "Create a new account to get started",
  };
  return <WelcomePage props={prop} />;
}
