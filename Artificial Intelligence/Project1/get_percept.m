function p = get_percept(action)
  
  global e m n posx posy

  switch  action
      case 's'
          e(posy,posx) = 0; 
      case 'l'
          posx = max(posx-1,1);
      case 'r'
          posx = min(posx+1,n);      
      case 'd'
          posy = min(posy+1,m);
  endswitch
  
  p = [posy posx e(posy,posx)];
  
 endfunction
 