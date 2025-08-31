# Music Genre Classification using a Convolutional Neural Network (CNN)

This project demonstrates the complete pipeline for building a machine learning model to classify the genre of a music track from its audio signal. It uses a Convolutional Neural Network (CNN) built with PyTorch to perform image classification on Mel-spectrograms generated from audio files.

## Problem Statement

Manually categorizing music is subjective and time-consuming. This model automates the process by learning to identify patterns in audio that are characteristic of different genres.

## Dataset

The model is designed to be trained on the **GTZAN Genre Collection dataset**.
*   **Content:** 1000 audio tracks, each 30 seconds long.
*   **Genres:** 10 genres, with 100 tracks per genre (Blues, Classical, Country, Disco, Hip-hop, Jazz, Metal, Pop, Reggae, Rock).
*   **Download:** The dataset can be downloaded from [Kaggle](https://www.kaggle.com/datasets/andradaolteanu/gtzan-dataset-music-genre-classification).

## The Technical Pipeline

1.  **Audio Preprocessing:**
    *   Audio files (`.wav`) are loaded using the `Librosa` library.
    *   Each audio signal is converted into a **Mel-spectrogram**, which is a visual representation of the audio's frequency content over time. This effectively transforms the audio classification problem into an image classification problem.

    
    *(A sample Mel-spectrogram for a Blues track)*

2.  **Model Architecture:**
    *   A custom **Convolutional Neural Network (CNN)** was built using `PyTorch`.
    *   The architecture consists of multiple convolutional layers for feature extraction, `ReLU` activation functions for non-linearity, `MaxPool2d` layers for down-sampling, and fully-connected layers for final classification.

3.  **Training and Results:**
    *   The model was trained on the generated spectrograms.
    *   It achieved a **training accuracy of 97.23%**, demonstrating its ability to effectively learn distinguishing features from the audio data.
    *   The `song_identifier.ipynb` notebook contains the full implementation, from data processing to model training and evaluation.

## How to Run
1.  Clone the repository.
2.  Install the required packages: `pip install -r requirements.txt`.
3.  Download the GTZAN dataset and place it in a known directory.
4.  Open and run the `song_identifier.ipynb` Jupyter Notebook, updating the dataset path as needed.