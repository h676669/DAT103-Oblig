section .data
  foo db '7368412x509'
  bar db 10

section .text 
global _start

_start:
  mov ecx,foo
  add ecx,10  ; <-- change this line 
  push ecx

  mov ecx,foo
  add ecx,7
  mov [ecx],byte 54 ; <-- change this line 
  mov edx,1
  mov ebx,1
  mov eax,4
  int 80h

  add ecx,0 ; <-- change this line 
  mov edx,1
  mov ebx,1
  mov eax,4
  int 80h

  pop ecx
  mov edx,1
  mov ebx,1
  mov eax,4
  int 80h

  mov edx,1
  mov ecx,bar
  mov ebx,1
  mov eax,4
  int 80h
  mov ebx,0
  mov eax,1
  int 80h
