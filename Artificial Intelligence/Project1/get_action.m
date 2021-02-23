
function action = get_action(p)

  global I m n posx posy reverse
  
  I(posy,posx) = p(3);

  if p(3) == 1
      action = 's';
  elseif sum(sum(I)) == 0
      action = 'n';
  elseif (posx == n && reverse == 0) || (posx == 1 && reverse == 1)
      action = 'd';
      reverse = !reverse;
  elseif (reverse == 1)
      action = 'l';
  else
      action = 'r';
  endif

endfunction
