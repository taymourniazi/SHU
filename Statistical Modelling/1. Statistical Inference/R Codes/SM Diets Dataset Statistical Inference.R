#reading the Data
dataset = read_excel('Diets.xlsx')
#feeling data
dataset
#to see how many diets were applied
unique(dataset$Diet)
# F Test to Compare Two Variances
var.test(dataset$Wtloss~dataset$Diet, alt="two.sided", conf.level=0.95)
# T test for group A & B with variance not equal
t.test(dataset$Wtloss~dataset$Diet, alt="two.sided", var.equal=FALSE, conf.level=0.95)
