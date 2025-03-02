# Real-Time Fact Check and Image Verification Extension

A Chrome extension that helps users verify content and images on web pages using Google's Fact Check API and Reverse Image Search.

## Features

- Real-time fact-checking of selected text
- Reverse image search for clicked images
- Clean and modern user interface
- Easy-to-use popup interface

## Installation

1. Clone this repository
2. Open Chrome and go to `chrome://extensions/`
3. Enable "Developer mode" (toggle in top right)
4. Click "Load unpacked"
5. Select the extension directory

## Setup

1. Get your Google API key from [Google Cloud Console](https://console.cloud.google.com/)
2. Get your Search Engine ID from [Google Programmable Search Engine](https://programmablesearch.google.com/create/new)
3. Update `background.js` with your Search Engine ID

## Usage

### Fact Checking
1. Select text on any webpage
2. Click the extension icon
3. Click "Check Facts"
4. View fact-checking results

### Image Verification
1. Click on any image on a webpage
2. Click the extension icon
3. Click "Check Image"
4. View similar images and sources

## Technologies Used

- HTML5
- CSS3
- JavaScript
- Chrome Extension APIs
- Google Fact Check API
- Google Custom Search API

## Contributing

Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

## License

[MIT](https://choosealicense.com/licenses/mit/) 