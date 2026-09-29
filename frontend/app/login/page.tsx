"use client";
import WelcomePage from "@/components/WelcomPage";

export default function LoginPage() {
  const prop = {
    register: false,
    login: true,
    title: "Welcome Back",
    description: "Sign in to your account to continue",
  };
  return <WelcomePage props={prop} />;
}
