# Change Log for rocSOLVER

Full documentation for rocSOLVER is available at [rocsolver.readthedocs.io](https://rocsolver.readthedocs.io/en/latest/).

## [(Unreleased) rocSOLVER for ROCm 4.3.0]
### Added
- Linear solvers for general non-square systems:
    - GELS now supports underdetermined and transposed cases
- Inverse of triangular matrices
    - TRTRI (with batched and strided\_batched versions)
- Out-of-place general matrix inversion
    - GETRI\_OUTOFPLACE (with batched and strided\_batched versions)

### Optimizations
- Improved general performance of matrix inversion (GETRI)

### Changed
- Argument names for the benchmark client now match argument names from the public API

### Removed

### Fixed
- Fixed known issues with Thin-SVD. The problem was identified in the test specification, not in the thin-SVD
  implementation or the rocBLAS gemm\_batched routines.
- Benchmark client will no longer crash as a result of leading dimension or stride arguments not being provided
  on the command line.

### Known Issues


## [rocSOLVER 3.12.0 for ROCm 4.2.0]
### Added
- Multi-level logging functionality
- Implementation of the Thin-SVD algorithm
- Reductions of generalized symmetric- and hermitian-definite eigenproblems:
    - SYGS2, SYGST (with batched and strided\_batched versions)
    - HEGS2, HEGST (with batched and strided\_batched versions)
- Symmetric and hermitian matrix eigensolvers:
    - SYEV (with batched and strided\_batched versions)
    - HEEV (with batched and strided\_batched versions)
- Generalized symmetric- and hermitian-definite eigensolvers:
    - SYGV (with batched and strided\_batched versions)
    - HEGV (with batched and strided\_batched versions)

### Optimizations

### Changed
- Sorting method in STERF as original quick-sort was failing for large sizes.

### Removed
- Removed hcc compiler support

### Fixed
- Fixed GELS overwriting B even when info != 0
- Error when calling STEQR with n=1 from batched routines
- Added `roc::rocblas` to the `roc::rocsolver` CMake usage requirements
- Added rocblas to the dependency list of the rocsolver deb and rpm packages
- Fixed rocblas symbol loading with dlopen and the `RTLD_NOW | RTLD_LOCAL` options

### Known Issues
- Thin-SVD implementation is failing in some cases (in particular m=300, n=120) due to a possible
  bug in the gemm\_batched routines of rocBLAS.


## [rocSOLVER 3.11.0 for ROCm 4.1.0]
### Added
- Eigensolver routines for symmetric/hermitian matrices:
    - STERF, STEQR
- Linear solvers for general non-square systems:
    - GELS (API added with batched and strided\_batched versions. Only the overdetermined
      non-transpose case is implemented in this release. Other cases will return
      `rocblas_status_not_implemented` status for now.)
- Extended test coverage for functions returning info
- Changelog file
- Tridiagonalization routines for symmetric and hermitian matrices:
    - LATRD
    - SYTD2, SYTRD (with batched and strided\_batched versions)
    - HETD2, HETRD (with batched and strided\_batched versions)
- Sample code and unit test for unified memory model/Heterogeneous Memory Management (HMM)

### Optimizations
- Improved performance of LU factorization of small and mid-size matrices (n <= 2048)

### Changed
- Raised minimum requirement for building rocSOLVER from source to CMake 3.8
- Switched to use semantic versioning for the library
- Enabled automatic reallocation of memory workspace in rocsolver clients

### Removed
- Removed `-DOPTIMAL` from the `roc::rocsolver` CMake usage requirements. This is an internal
  rocSOLVER definition, and does not need to be defined by library users

### Fixed
- Fixed runtime errors in debug mode caused by incorrect kernel launch bounds
- Fixed complex unit test bug caused by incorrect zaxpy function signature
- Eliminated a small memory transfer that was being done on the default stream
- Fixed GESVD right singular vectors for 1x1 matrices


## [rocSOLVER 3.10.0 for ROCm 3.10.0]
### Added
- Orthonormal/Unitary matrix generator routines (reverse order):
    - ORG2L, UNG2L, ORGQL, UNGQL
    - ORGTR, UNGTR
- Orthonormal/Unitary matrix multiplications routines (reverse order):
    - ORM2L, UNM2L, ORMQL, UNMQL
    - ORMTR, UNMTR

### Changed
- Major library refactoring to adopt rocBLAS memory model

### Fixed
- Returned values in parameter info of functions dealing with singularities



## [rocSOLVER 3.9.0 for ROCm 3.9.0]
### Added
- Improved debug build mode for developers
- QL factorization routines:
    - GEQL2, GEQLF (with batched and strided\_batched versions)
- SVD of general matrices routines:
    - GESVD (with batched and strided\_batched versions)

### Optimizations
- Improved performance of mid-size matrix inversion (64 < n <= 2048)



## [rocSOLVER 3.8.0 for ROCm 3.8.0]
### Added
- Sample codes for C, C++ and FORTRAN
- LU factorization without pivoting routines:
    - GETF2\_NPVT, GETRF\_NPVT (with batched and strided\_batched versions)

### Optimizations
- Improved performance of LU factorization of mid-size matrices (64 < n <= 2048)
- Improved performance of small-size matrix inversion (n <= 64)

### Fixed
- Ensure the public API is C compatible


## [rocSOLVER 3.7.0 for ROCm 3.7.0]
### Added
- LU-factorization-based matrix inverse routines:
    - GETRI (with batched and strided\_batched versions)
- SVD of bidiagonal matrices routine:
    - BDSQR

### Fixed
- Ensure congruency on the input data when executing performance tests (benchmarks)



## [rocSOLVER 3.6.0 for ROCm 3.6.0]
### Added
- Complex precision support for all existing rocSOLVER functions
- Bidiagonalization routines:
    - LABRD
    - GEBD2, GEBRD (with batched and strided\_batched versions)
- Integration of rocSOLVER to hipBLAS

### Optimizations
- Improved performance of LU factorization of tiny matrices (n <= 64)

### Changed
- Major clients refactoring to achieve better test coverage and benchmarking



## [rocSOLVER 3.5.0 for ROCm 3.5.0]
### Added
- Installation script and new build procedure
- Documentation and integration with ReadTheDocs
- Orthonormal matrix multiplication routines:
    - ORM2R, ORMQR
    - ORML2, ORMLQ
    - ORMBR

### Changed
- Switched to use all rocBLAS types and enumerations
- Major library refactoring to achieve better integration and rocBLAS support
- hip-clang is now default compiler

### Deprecated
- rocSOLVER types and enumerations
- hcc compiler support

