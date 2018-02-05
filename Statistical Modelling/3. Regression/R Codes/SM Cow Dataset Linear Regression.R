#Read Data
dataset=read.csv('cow.csv')
#know how of dataset
names(dataset)
#Applying Linear Regression(here y comes first then x)
regressor=lm(dataset$weight~dataset$time)
#brief of linear regression model
summary(regressor)
#finding intercept and slope
coef(regressor)
#Applying Linear Regression
plot(dataset$time, dataset$weight, main = 'Scatter Plot', xlab = 'Time', ylab = 'Weight' )
#plotting regression line
abline(regressor, col='blue', lwd=2)
