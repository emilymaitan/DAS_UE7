#### DAS UE7 Test Script ####
setwd("C:/Users/Emily/workspace/IntelliJ/DAS_UE7/src/DAS/Data")
values1 <- as.matrix(read.csv("integers1.txt", header=FALSE))
values2 <- as.matrix(read.csv("integers2.txt", header=FALSE))

print("BASIC STATS - FILE 1")
print(basicStats(values1))
print("BASIC STATS - FILE 2")
print(basicStats(values2))

boxplot(c(as.data.frame(values1),as.data.frame(values2)),main="Boxplots", names = c("File 1", "File 2"))

plot(values1, values2)
abline(lsfit(values1, values2), col="blue", lwd=2)
title(main="Linearer Trend?")
print(paste("Korrelationskoeffizient: ",cor(values1,values2)))

print("### Unit Testing ###")
neg <- c(-1,-10,-5,10,-50)
print(basicStats(neg))
boxplot(neg)

test <- c(1,5,10,20,100,0)
print(basicStats(neg))
boxplot(neg)
