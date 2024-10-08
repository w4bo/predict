#!/bin/bash
set -exo
# if [ -d "venv" ]; then
#   # Take action if $DIR exists.
# else
#   sudo apt-get install -y python3-venv # install python venv
#   python -m venv venv # create the virtual environment
#   pip install -r requirements.txt
# fi
source venv/bin/activate # activate the virtual environment
python3 -m unittest -f TestPredict.py
python3 gen_cube.py
python3 -m unittest -f TestAssess.py
python3 -m unittest -f TestAssessExt.py
python3 -m unittest -f TestExplain.py
