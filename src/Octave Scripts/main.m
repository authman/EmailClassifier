%%%%%%%%%%%   the main  script  ******
load XTraining.txt;
load yTraining.txt;
%%size(X)
%%size(y)
%% need to pad a columns of 1 on the lefthand side of X
XTraining=[ones(size(XTraining,1),1),XTraining];
theta= zeros(size(XTraining,2),1);  %% initial value of theta

%%Experimented with various steps sizes. 0.1 was the best balance between speed and accuracy
step = 0.10; 
page_output_immediately = 1;
for count=1:50
    theta=newiteration(XTraining,yTraining,theta,step);
    fflush(stdout);
endfor
save -ascii Theta.mat theta

