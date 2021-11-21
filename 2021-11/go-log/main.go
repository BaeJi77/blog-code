package main

import (
	"log"
	"os"
)

func main() {
	log.Println("hello")

	logger11 := log.New(os.Stdout, "hello:::::", log.LUTC|log.Llongfile)
	logger11.Println("hello")

	file, _ := os.OpenFile("hello.log", os.O_CREATE|os.O_RDWR, 0644)
	logger11.SetOutput(file)
	logger11.Println("hello")
}
