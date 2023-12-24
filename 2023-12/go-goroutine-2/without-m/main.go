package main

import (
	"fmt"
	"runtime"
	"sync"
	"time"
)

func goroutineSleep(wg *sync.WaitGroup) {
	defer wg.Done()
	time.Sleep(100 * time.Second)
}

func main() {
	wg := &sync.WaitGroup{}
	for i := 0; i < 1000; i++ {
		wg.Add(1)
		go goroutineSleep(wg)
	}

	time.Sleep(time.Second)

	fmt.Printf("goroutines: %d, get M number %d\n", runtime.NumGoroutine(), runtime.GOMAXPROCS(0))

	wg.Wait()
}
