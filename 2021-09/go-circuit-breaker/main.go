package main

import (
	"log"
	"time"
)

func main() {
	log.Println("Hello world!")

	cb := NewFailureCountThresholdBreaker(1, 3*time.Second)

	log.Printf("cb.IsAvailable()=%t\n", cb.IsAvailable())

	cb.RecordFailure()
	log.Printf("cb.IsAvailable()=%t\n", cb.IsAvailable())

	cb.RecordFailure()
	log.Printf("cb.IsAvailable()=%t\n", cb.IsAvailable())

	time.Sleep(5 * time.Second)
	log.Printf("cb.IsAvailable()=%t\n", cb.IsAvailable())

	cb.Reset()
	log.Printf("cb.IsAvailable()=%t\n", cb.IsAvailable())
}
