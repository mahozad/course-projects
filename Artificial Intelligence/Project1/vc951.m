
global e I m n posy posx reverse % e: clean or dirty

m = 5;
n = 8;

I = ones(m,n);
e = fix(2*rand(m,n))
posx = 1;
posy = 1;
reverse = 0;
cnt = 0;
flag = 1;
percept = [posy,posx,e(posy,posx)];

while 1
	cnt = cnt + 1
  percept, 
	action = get_action(percept),pause
	if action == 'n'
    break;
  endif
	percept = get_percept(action);
  disp(e)
endwhile
