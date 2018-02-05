#Read Data
dataset=read.csv('sales.csv')
#Applying Linear Regression(here y comes first then x)
regressor=lm(dataset$credit~dataset$income)
#brief of linear regression model
summary(regressor)
#Applying Linear Regression
plot(dataset$income, dataset$credit, main = 'Scatter Plot for Sales dataset',
     xlab = 'Income', ylab = 'Credit' )
#plotting regression line
abline(regressor, col='blue', lwd=2)