"use client";
import WelcomePage from "@/components/WelcomPage";
export default function CreateAccountPage() {
  const prop = {
    register: true,
    login: false,
    title: "Get Started",
    description: "Create a new account to get started",
  };
  return <WelcomePage props={prop} />;
}
