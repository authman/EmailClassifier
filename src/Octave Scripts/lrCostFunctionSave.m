function [J, grad] = lrCostFunction(theta, X, y, lambda)
%LRCOSTFUNCTION Compute cost and gradient for logistic regression with 
%regularization
%   J = LRCOSTFUNCTION(theta, X, y, lambda) computes the cost of using
%   theta as the parameter for regularized logistic regression and the
%   gradient of the cost w.r.t. to the parameters. 
m=size(y,1);
size(theta);
 
J = 0;
grad = zeros(size(theta));


for i=1:m
	temp=sigmoid(dot(theta',X(i,:))); 
	J = J+ (-y(i,:)*log(temp)- (1-y(i,:))*log(1- temp));
	grad=grad+(temp-y(i,1))*X(i,:)';
	
end
J=J/m;
grad=grad/m + lambda* theta/m; % includes theta 1
grad(1) = grad(1) - lambda*theta(1)/m; % offseting transaction
temp=theta;
temp(1)=0;
delta=(dot(temp,temp))*lambda/(2*m);
J=J+delta;

% =============================================================
grad = grad(:);  % coverts row vector to a column venctor

end
