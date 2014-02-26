%%%%   validation %%%%%

load Theta.mat
%% Theta now loaded
load XValidate.txt
load yValidate.txt
XValidate=[ones(size(XValidate,1),1),XValidate];
count=size(yValidate,1);
success=0;
falsepositives=0;
falsenegatives=0;
for i=1:count;
	z=XValidate(i,:)*Theta;

	if sigmoid(z) > 0.5 
		predict = 1;
	else
		predict = 0;
	endif;
	
	if yValidate(i,1)==predict
		success=success+1;
	else
		if  predict == 1
			%% we predicted 1 but was 0
			falsepositives= falsepositives+1;
		else
			falsenegatives=falsenegatives+1;
		endif
	endif
endfor
totalcount=count
successratio = success*1.0/count
falsepositives
falsenegatives

