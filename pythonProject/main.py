from flask import Flask, request, jsonify
from dostoevsky.tokenization import RegexTokenizer
from dostoevsky.models import FastTextSocialNetworkModel

app = Flask(__name__)

tokenizer = RegexTokenizer()
model = FastTextSocialNetworkModel(tokenizer=tokenizer)


def analyze_text(text):
    result = model.predict([text], k=2)
    categories = result[0].keys()

    if 'positive' in categories and result[0]['positive'] > 0.5:
        return 'positive'
    elif 'negative' in categories and result[0]['negative'] > 0.5:
        return 'negative'
    else:
        return 'neutral'


@app.route('/analyze', methods=['POST'])
def analyze_endpoint():
    try:
        data = request.get_json()
        texts = data.get('texts', [])

        if not texts:
            return jsonify({'error': 'No texts provided'}), 400

        results = dict([])
        for text in texts:
            results[text] = analyze_text(text)

        return jsonify({'results': results}), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)