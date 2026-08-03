"use client";
import WelcomePage from "@/components/WelcomPage";



export default function LoginPage() {
    const prop = {
        firstName: false,
        lastName: false,
        title: "Welcome Back",
        description: "Sign in to your account to continue"
    }
  return <WelcomePage props={prop}/>;
}
