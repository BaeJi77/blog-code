# echo "GET http://127.0.0.1:9997/" | vegeta attack -duration=5s | vegeta report 

# echo "GET http://127.0.0.1:9997/" | vegeta attack -duration=5s | vegeta report -type=json > result.json

# echo "GET http://127.0.0.1:9997/" | vegeta attack -duration=5s | vegeta report -type='hist[0, 2ms, 4ms, 6ms, 8ms, 10ms]'

# multi host
# vegeta attack -rate=1 -duration=5s -targets=targets.txt | vegeta report 

# vegeta attack -rate=1000 -duration=5s -targets=targets.txt | vegeta report -type='hist[0, 2ms, 4ms, 6ms, 8ms, 10ms]'

# vegeta attack -rate=10 -duration=5s -targets=targets.txt | vegeta report -type=json > result.json

# plot
# vegeta attack -name=50qps -rate=50 -duration=5s -targets=targets_2.txt > results.50qps.bin
# cat results.50qps.bin | vegeta plot > plot.50qps.html

# rate=0
# vegeta attack -rate=0 -max-workers=5 -duration=5s -targets=targets.txt | vegeta report 
