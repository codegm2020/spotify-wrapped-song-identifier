import torch.nn as nn

class GenreCNN(nn.Module):
    def __init__(self, num_genres=10):
        super(GenreCNN, self).__init__()
        # Convolutional layers
        self.conv_layers = nn.Sequential(
            # Conv Block 1
            nn.Conv2d(in_channels=1, out_channels=16, kernel_size=3, stride=1, padding=1),
            nn.ReLU(),
            nn.MaxPool2d(kernel_size=2, stride=2),
            nn.BatchNorm2d(16),

            # Conv Block 2
            nn.Conv2d(in_channels=16, out_channels=32, kernel_size=3, stride=1, padding=1),
            nn.ReLU(),
            nn.MaxPool2d(kernel_size=2, stride=2),
            nn.BatchNorm2d(32),

            # Conv Block 3
            nn.Conv2d(in_channels=32, out_channels=64, kernel_size=3, stride=1, padding=1),
            nn.ReLU(),
            nn.MaxPool2d(kernel_size=4, stride=4), # Larger pooling
            nn.BatchNorm2d(64)
        )
        
        # Flatten the output for the fully connected layers
        self.flatten = nn.Flatten()

        # Fully connected layers
        self.fc_layers = nn.Sequential(
            nn.Linear(in_features=64 * 8 * 4, out_features=128), # Adjust in_features based on spectrogram size
            nn.ReLU(),
            nn.Dropout(0.5),
            nn.Linear(in_features=128, out_features=num_genres)
        )

    def forward(self, x):
        # Pass input through conv layers
        x = self.conv_layers(x)
        # Flatten the output
        x = self.flatten(x)
        # Pass through fully connected layers
        logits = self.fc_layers(x)
        return logits