export default function isFullPage(pathname) {
  if (pathname === '/freeview' || pathname === '/freeview/' || pathname === '/freeview/login') { return true; }
  return false;
}
