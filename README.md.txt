# Agent Payment Management System

## 📌 Project Description
This project is a **Java console application** designed to manage **agents**, their **departments**, and their **payments**. The goal is to provide agents and department managers with a simple, reliable, and secure tool to manage **salaries, bonuses, allowances, and premiums**, while keeping detailed records and statistics.

---

## 🎯 Main Objectives & Features

### For Agents:
- View personal information and assigned department.
- Display full payment history.
- Filter and sort payments by type, amount, or date.
- Calculate the total of all payments.

### For Department Managers:
- Create, edit, or delete departments and assign a manager.
- Add, modify, or remove agents and assign them to departments.
- Add a payment to an agent:
  - **Salary and premium**: available for all agents.
  - **Bonus and allowance**: only available if the agent is eligible and the required condition is fulfilled.
- View and filter payments for one agent or all agents in the department.
- Automatically calculate total and average payments by agent and department.
- Identify unusual or incorrect payments.

### Statistics:
- Total payments by agent and by department.
- Highest and lowest payment values.
- Ranking of agents by total payment received.
- Payment distribution by type (salary, bonus, allowance, premium).

---

## 🏗️ Technical Architecture

- **Model**: `Agent`, `Department`, `Payment`, `TypeAgent`, `TypePayment`.
- **View**: Console interface for agents and managers.
- **Controller**: Handles user interactions, command processing, and validation.
- **Service**: Business logic and payment processing.
- **Collections**: `ArrayList` for agents, departments, and payments.
- **Streams & Lambdas**: Filtering, sorting, aggregation.
- **Functional Interfaces**: `Predicate`, `Function`, `Consumer`, `Supplier`.
- **Optional**: Handles absent values safely.
- **Java Time API**: Manages payment dates.
- **Exception Handling**: Negative amounts, missing agents or departments, unmet bonus/allowance conditions.
- **Persistence**: JDBC for data storage and retrieval.

---

## 🗂️ Package Structure

