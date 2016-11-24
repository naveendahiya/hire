(function() {
    'use strict';

    angular
        .module('hireApp')
        .controller('CandidateJobordrerStatusTypeController', CandidateJobordrerStatusTypeController);

    CandidateJobordrerStatusTypeController.$inject = ['$scope', '$state', 'CandidateJobordrerStatusType'];

    function CandidateJobordrerStatusTypeController ($scope, $state, CandidateJobordrerStatusType) {
        var vm = this;

        vm.candidateJobordrerStatusTypes = [];

        loadAll();

        function loadAll() {
            CandidateJobordrerStatusType.query(function(result) {
                vm.candidateJobordrerStatusTypes = result;
            });
        }
    }
})();
