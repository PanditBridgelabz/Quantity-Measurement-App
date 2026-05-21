function ResultCard({ result }) {
  if (!result) return null

  return (
    <div className="result-card">
      <h2>Result</h2>

      <p>
        <strong>Value:</strong> {result.value}
      </p>

      <p>
        <strong>Unit:</strong> {result.unit}
      </p>

      <p>
        <strong>Type:</strong> {result.type}
      </p>
    </div>
  )
}

export default ResultCard