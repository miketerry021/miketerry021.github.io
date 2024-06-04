#ifndef AIRGEADBANKINGFUNCTIONS_H
#define AIRGEADBANKINGFUNCTIONS_H
using namespace std;

//Function declarations for this program.
void financialInput();
void balanceWithoutMonthlyDeposit(int t_years, double t_initialAmt, double t_interestRate);
void balanceWithMonthlyDeposit(int t_years, double t_initialAmt, double t_interestRate, double t_monthlyDeposit);
void displayCalculatedOutput(int t_years, double t_ytdInterestEarned, double t_ytdBalance);

#endif
