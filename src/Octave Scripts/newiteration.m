function newtheta = newiteration(X,y,theta,step)
% This function performs one gradient descent iteration for logical regression
% with X,Y, theta, step as input.
% it return updated theta and a new value of the cost, hopefully lower 
%
% if there are n  features and m samples
% X is a [m,n+1] matrix : x(m,1) is always 1
% Y is a m sized vector: by definition must be 0 or 1
% theta is a n+1 vector, 
% is OK to give an arbitrary high value to oldcost for the first iteration
% 

[J,grad] = lrCostFunction(theta, X,y, 0.15);
J   % print value of J
newtheta = theta - step*J*grad;



